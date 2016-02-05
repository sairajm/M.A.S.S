package com.mass.sairajmadhavan.mass;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

//import android.widget.EditText;

//import static com.mass.sairajmadhavan.mass.R.id.edit_message;


public class HomeActivity extends ActionBarActivity {
//public final static String EXTRA_MESSAGE="com.mass.sairajmadhavan.mass.MEDNAME";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void newsFeed(View view)
    {
        Intent intent=new Intent(this, DisplayNewsActivity.class);
        /*EditText enteredText= (EditText)findViewById(edit_message);
        String medName=enteredText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE,medName);*/
        startActivity(intent);
    }

    public void hospitalFinder(View view){
        Intent intent=new Intent(this, DisplayHospitalActivity.class);
        startActivity(intent);
    }

    public void pharmacyFinder(View view){
        Intent intent=new Intent(this, DisplayPharmacyActivity.class);
        startActivity(intent);
    }

    public void AmbulanceDispatch(View view){

        Intent intent=new Intent(this, Ambulance.class);
        startActivity(intent);
    }
}
