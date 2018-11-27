package com.nibrasco.freshapp;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.*;
import com.nibrasco.freshapp.Model.User;

public class SignUp extends AppCompatActivity {

    EditText edtPhone, edtName, edtPwd;
    Button btnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        edtPhone = (EditText)findViewById(R.id.edtPhone);
        edtPwd = (EditText)findViewById(R.id.edtPwd);
        edtName = (EditText)findViewById(R.id.edtName);
        btnSignUp = (Button)findViewById(R.id.btnSignUp);

        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference tblUser = db.getReference("User");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog mDialog =new ProgressDialog(SignUp.this);
                mDialog.setMessage("Registering Your Account...");
                mDialog.show();
                tblUser.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(!dataSnapshot.child(edtPhone.getText().toString()).exists()) {
                            mDialog.dismiss();

                            String name = edtName.getText().toString(),
                                    pwd = edtPwd.getText().toString(),
                                    phone = edtPhone.getText().toString();
                            User user = new User(name, pwd);
                            tblUser.child(phone).setValue(user);
                            Toast.makeText(SignUp.this, "Account Registered Successfully", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            mDialog.dismiss();
                            Toast.makeText(SignUp.this,
                                    "Phone number already exists, please choose another phone number!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
