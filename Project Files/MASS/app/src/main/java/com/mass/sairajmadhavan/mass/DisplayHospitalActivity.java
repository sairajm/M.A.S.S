package com.mass.sairajmadhavan.mass;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.Math.acos;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

public class DisplayHospitalActivity extends ListActivity {
    private static String url_all_products = "http://mass.0fees.us/get_all_hospitals.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCTS = "hospitals";
    private static final String TAG_PID = "latitude";
    private static final String TAG_NAME = "longitude";
    private static final String TAG_ADDRESS="address";
    private static final String TAG_HOSPITAL="name";
    private static final String TAG_PHNO="phno";
    private static String hosptype;
    private ProgressDialog pDialog;
    Double latitude;
    Double longitude;
    String lats;
    String longs;
    int count=0;
    Double distance;
    JSONParser jParser = new JSONParser();
    private GoogleMap googleMap;
    GPSTracker gps;
    Double lat;
    Double lon;
    JSONArray products = null;
    Intent In=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_display_hospital);
        final ListView listview = getListView();
        String[] values = new String[] { "Maternity Clinic", "Cancer Care", "Cardiology Care",
                "Childrens Hospital", "Dialysis Centres", "Eye Hospitals", "General Hospitals", "Neurology Hospitals",
                "Orthopaedic Hospitals", "Psychiatric Hospitals", "Tuberculosis Hospitals", "Veterinary Hospitals" };

        final ArrayList<String> list = new ArrayList<String>();
        for(int i = 0; i < values.length;++i) {
            list.add(values[i]);
        }
        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                hosptype = (String) listview.getItemAtPosition(position);
                new LoadAllProducts().execute();
            }

        });
        gps = new GPSTracker(DisplayHospitalActivity.this);
        if(gps.canGetLocation())
        {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
        }

        else
        {
            // can't get location
            // GPS or Network is not enabled
            gps.showSettingsAlert();
        }
lats=latitude.toString();
        longs=longitude.toString();

    }
    @Override
    protected void onStop() {
        super.onStop();

        if(pDialog!= null)
            pDialog.dismiss();
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_hospital, menu);
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
    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }
    class LoadAllProducts extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //  if(pDialog!=null){
            pDialog = new ProgressDialog(DisplayHospitalActivity.this);
            pDialog.setMessage("Loading Hospitals on Google Maps. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();//}
        }

        /**
         * getting All products from url
         * */
        protected String doInBackground(String... args) {
            // Building Parameters

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("hosptype", hosptype));
            params.add(new BasicNameValuePair("latitude",lats));
           params.add(new BasicNameValuePair("longitude",longs));
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_all_products, "POST", params);
            In = new Intent(DisplayHospitalActivity.this, PharmMapDisplay.class);
            // Check your log cat for JSON reponse
            Log.e("All Hospitals: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    products = json.getJSONArray(TAG_PRODUCTS);

                    // looping through All Products
                    for (int i = 0; i < products.length(); i++) {
                        JSONObject c = products.getJSONObject(i);

                        // Storing each json item in variable
                        String id = c.getString(TAG_PID);   //latitude
                        String name = c.getString(TAG_NAME);  //longitude
                        String HospName=c.getString(TAG_HOSPITAL);  //Hospital Name
                        String address=c.getString(TAG_ADDRESS);  // Hospital address
                        String Phno=c.getString(TAG_PHNO);  //Hospital Phone Number
                        lat=Double.parseDouble(id);
                        lon=Double.parseDouble(name);


                        distance=( 6371 * acos( cos( toRadians( latitude ) ) * cos( toRadians( lat ) ) * cos( toRadians( lon ) - toRadians( longitude ) ) + sin( toRadians( latitude ) ) * sin( toRadians( lat ) ) ) );
                        while(distance<10&&count<6) {
                            LatLng point = new LatLng(lat, lon);
                            In.putExtra("lati", lat);
                            In.putExtra("longi", lon);
                            // In.putExtra("LatLng",point);
                            In.putExtra("name", HospName);
                            In.putExtra("address", address);
                            In.putExtra("phno", Phno);
                            startActivity(In);
                            count++;
                            break;
                        }

                        // creating new HashMap


                        // adding HashList to ArrayList
                        //productsList.add(map);
                    }

                    finish();

                } else {
                    // no products found
                    // Launch Add New product Activity
                    //nothing to do here for now..
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }


        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
// dismiss the dialog after getting all products

            // if(pDialog.isShowing()){
            pDialog.dismiss();//}
            //  pDialog = null;}
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                              public void run() {



                                  /**
                                   * Updating parsed JSON data into ListView
                                   * */
                                 /* ListAdapter adapter = new SimpleAdapter(
                                          AllProductsActivity.this, productsList,
                                          R.layout.list_item, new String[]{TAG_PID,
                                          TAG_NAME},
                                          new int[]{R.id.pid, R.id.name});
                                  // updating listview
                                  setListAdapter(adapter);*/
                              }
                          }



            );


        }

    }




}
