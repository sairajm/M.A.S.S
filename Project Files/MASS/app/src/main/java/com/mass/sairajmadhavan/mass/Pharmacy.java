package com.mass.sairajmadhavan.mass;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by Sairaj Madhavan on 2/23/2015.
 */
public class Pharmacy extends ActionBarActivity {

    int _id;
    String _pharma_name;
    String _medname;
    double _lat;
    double _lng;
    int _availability;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    Intent nameIntent= getIntent();
    _medname= nameIntent.getStringExtra(DisplayPharmacyActivity.EXTRA_MESSAGE);  // The medicine name is being obtained from the user.
}

    public Pharmacy(){} // empty Constructor

public String MedicineName(){

    return _medname;
}
    //variable initialization using constructor

    public Pharmacy(int id, String pharmaname, double lat, double lng, int availability){

        this._id= id;
        this._lat=lat;
        this._lng=lng;
        this._pharma_name=pharmaname;
        this._availability=availability;
    }

    // getting ID
    public int getID(){
        return this._id;
    }

    // setting id
    public void setID(int id){
        this._id = id;
    }

    // getting name
    public String getMedName(){
        return this._medname;
    }

    // setting name
  /*  public void setMedName(String name){
        this._medname = name;
    }

    // getting phone number*/
    public String getPharmaName(){
        return this._pharma_name;
    }

    // setting pharmacy name
    public void setPharmaName(String pharmacy_name){
        this._pharma_name = pharmacy_name;
    }


    public double getLatitude(){
        return this._lat;

    }

    public void setLatitude(double Latitude){

        this._lat=Latitude;
    }

    public double getLongitude(){
        return this._lng;

    }

    public void setLongitude(double Longitude){

        this._lng=Longitude;
    }

    public int getAvailability(){

        return _availability;
    }

    public void setAvailability(int Avail){

        this._availability=Avail;
    }
}
