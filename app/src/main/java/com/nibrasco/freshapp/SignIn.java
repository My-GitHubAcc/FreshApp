package com.nibrasco.freshapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignIn extends AppCompatActivity {

    EditText edtPhone, edtPwd;
    Button btnSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        edtPhone = (EditText)findViewById(R.id.edtPhone);
        edtPwd = (EditText)findViewById(R.id.edtPwd);

        btnSignIn = (Button)findViewById(R.id.btnSignIn);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog mDialog =new ProgressDialog(SignIn.this);
                mDialog.setMessage("Authenticating...");
                mDialog.show();
            }
        });
    }
}
