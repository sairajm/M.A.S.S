package com.mass.sairajmadhavan.mass;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DisplayPharmacyActivity extends ActionBarActivity {
    private EditText mednameField;
    Button getButton;
String medname;
    public final static String EXTRA_MESSAGE = "com.mass.sairajmadhavan.mass.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_pharmacy);
    }
        //getButton = (Button) (findViewById(R.id.button4));






        public void loginPost(View view) {
            // Launching All products Activity
            Intent i = new Intent(this, AllProductsActivity.class);
            mednameField = (EditText) findViewById(R.id.medText);
          //  Intent i = new Intent(getApplicationContext(), AllProductsActivity.class);
            medname = mednameField.getText().toString();
            //Intent intent=new Intent(this, AllProductsActivity.class);
            new AllProductsActivity(medname);

           // i.putExtra(EXTRA_MESSAGE,medname);
            startActivity(i);

        }




    }




