package com.mass.sairajmadhavan.mass;

//import android.app.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;


public class Pharmacy_map extends Activity {

    private ListView listView;
private static int normal=1;
String[] tableval=new String[100];
    int n=0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_map);

        DatabaseHandler db = new DatabaseHandler(this);

        /**
         * CRUD Operations
         * */
        // Inserting Contacts
        Log.d("Insert: ", "Inserting ..");
        db.addPharmacy(new Pharmacy(1,"MedPlus", 12.984388, 80.193407, 1));
        db.addPharmacy(new Pharmacy(2,"Gokul Medicals", 12.984422, 80.193396, 1));
        db.addPharmacy(new Pharmacy(3,"Arasu Medicals", 12.9859526, 80.1859338, 1));
        db.addPharmacy(new Pharmacy(4,"Tulasi Medicals", 12.985663,  80.188383, 1));

        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
       List<Pharmacy> pharmacies = db.getAllPharmacies();

        if(pharmacies!=null){

            for(Pharmacy cn: pharmacies){

                String log = "Id: "+cn.getID()+" ,Name: " + cn.getPharmaName() + " ,Latitude: " + cn.getLatitude() +", Longtiude:" +cn.getLongitude();
tableval[n]=log.toString();
                double latitude;
                double longtiude;

                TextView textView=(TextView)findViewById(R.id.textView3);

               textView.setText(tableval[n]);
                n++;
            }

        }

/* ArrayAdapter<Pharmacy> adapter=new ArrayAdapter(this,android.R.layout.activity_list_item, pharmacies);
                ListView listView = (ListView) findViewById(R.id.list);
                listView.setAdapter(adapter);*/
  // ArrayAdapter<Pharmacy> adapter=new ArrayAdapter(this,android.R.layout.activity_list_item, pharmacies);
           // String output=adapter.toString();

         //  ListView listView = (ListView) findViewById(R.id.list);
          //  listView.setAdapter(adapter);
        //getListView().setTextFilterEnabled(true);


        /*for (Pharmacy cn : pharmacies) {
           TextView textView=(TextView)findViewById(R.id.textView3);
            String log = "Id: "+cn.getID()+" ,Name: " + cn.getPharmaName() + " ,Latitude: " + cn.getLatitude() +", Longtiude:" +cn.getLongitude
();*/
           // textView.setText(log);
            // Writing Contacts to log
            //Log.d("Name: ", log);





        db.close();

    }


    }

