package com.nibrasco.freshapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.nibrasco.freshapp.Model.Cart;
import com.nibrasco.freshapp.Model.Cart;

public class OrderItem extends AppCompatActivity {

    Spinner spSlicing, spWeight,spPackaging;
    EditText edtNotes, edtQuantity;
    TextView txtTotal;
    Button btnConfirm;
    CheckBox checkIntestine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_item);

        ArrayAdapter adapter = new ArrayAdapter<Cart.eSlicing>(this, android.R.layout.simple_spinner_item, Cart.eSlicing.values());
        spSlicing.setAdapter(adapter);
        int spinnerPosition = adapter.getPosition(Cart.eSlicing.None);
        spSlicing.setSelection(spinnerPosition);

        adapter = new ArrayAdapter<Cart.eWeight>(this, android.R.layout.simple_spinner_item, Cart.eWeight.values());
        spWeight.setAdapter(adapter);
        spinnerPosition = adapter.getPosition(Cart.eWeight.None);
        spWeight.setSelection(spinnerPosition);

        adapter = new ArrayAdapter<Cart.ePackaging>(this, android.R.layout.simple_spinner_item, Cart.ePackaging.values());
        spPackaging.setAdapter(adapter);
        spinnerPosition = adapter.getPosition(Cart.ePackaging.None);
        spPackaging.setSelection(spinnerPosition);

        checkIntestine.setChecked(false);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cart.Item item = new Cart.Item(
                        Cart.eCategory.Sheep,
                        Integer.parseInt(edtQuantity.getText().toString()),
                        checkIntestine.isChecked(),
                        edtNotes.getText().toString(),
                        Cart.eSlicing.Get(((Cart.eSlicing)(spSlicing.getSelectedItem())).Value()),
                        Cart.ePackaging.Get(((Cart.ePackaging)spPackaging.getSelectedItem()).Value()),
                        Cart.eWeight.Get(((Cart.eWeight)spWeight.getSelectedItem()).Value()),
                        Float.parseFloat(txtTotal.getText().toString())
                );
                //Add the item to the cart at this point
            }
        });
    }
}
