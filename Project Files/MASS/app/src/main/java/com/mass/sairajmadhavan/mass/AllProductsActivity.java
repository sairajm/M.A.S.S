package com.mass.sairajmadhavan.mass;

/**
 * Created by Sairaj Madhavan on 3/12/2015.
 */

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.android.gms.maps.GoogleMap;

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

public class AllProductsActivity extends ListActivity {

    private GoogleMap googleMap;
    GPSTracker gps;
    Double lat;
    Double lon;
    Double latitude;
    Double longitude;
String lats;
    String longs;
    int count;
    Double distance;
    // Progress Dialog
    private ProgressDialog pDialog;
    Intent i=getIntent();

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String, String>> productsList;

    // url to get all products list

    private static String url_all_products = "http://mass.0fees.us/get_all_pharmcies.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCTS = "pharmacy";
    private static final String TAG_PID = "lat";
    private static final String TAG_NAME = "longitude";
    private static final String TAG_PHARMA="name";
    private static String medname;
    // products JSONArray
    JSONArray products = null;
    public AllProductsActivity(){}
    public AllProductsActivity(String name){
        this.medname=name;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_products);

      // medname = i.getStringExtra(DisplayPharmacyActivity.EXTRA_MESSAGE);
        // Hashmap for ListView
        productsList = new ArrayList<HashMap<String, String>>();
        gps = new GPSTracker(AllProductsActivity.this);
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
        // Loading products in Background Thread
        new LoadAllProducts().execute();

        // Get listview
        ListView lv = getListView();

        // on seleting single product
        // launching Edit Product Screen
       /* lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                String pid = ((TextView) view.findViewById(R.id.pid)).getText()
                        .toString();

                // Starting new intent
                Intent in = new Intent(getApplicationContext(),
                        EditProductActivity.class);
                // sending pid to next activity
                in.putExtra(TAG_PID, pid);

                // starting new activity and expecting some response back
                startActivityForResult(in, 100);
            }
        });*/

    }

    // Response from Edit Product Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // if result code 100
        if (resultCode == 100) {
            // if result code 100 is received
            // means user edited/deleted product
            // reload this screen again
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }

    }
    @Override
    protected void onStop() {
        super.onStop();

        if(pDialog!= null)
            pDialog.dismiss();
    }

    /**
     * Background Async Task to Load all product by making HTTP Request
     * */
    class LoadAllProducts extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
          //  if(pDialog!=null){
            pDialog = new ProgressDialog(AllProductsActivity.this);
            pDialog.setMessage("Loading Pharmacies on GoogleMaps. Please wait...");
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
            params.add(new BasicNameValuePair("medname", medname));
            params.add(new BasicNameValuePair("latitude",lats));
            params.add(new BasicNameValuePair("longitude",longs));
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_all_products, "POST", params);

            // Check your log cat for JSON reponse
            Log.e("All Pharmacies: ", json.toString());

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
                        String id = c.getString(TAG_PID);
                        String name = c.getString(TAG_NAME);
                        String medicals=c.getString(TAG_PHARMA);
                        lat=Double.parseDouble(id);
                        lon=Double.parseDouble(name);
                        Intent In = new Intent(AllProductsActivity.this, PharmMapDisplay.class);
                        distance=( 6371 * acos( cos( toRadians( latitude ) ) * cos( toRadians( lat ) ) * cos( toRadians( lon ) - toRadians( longitude ) ) + sin( toRadians( latitude ) ) * sin( toRadians( lat ) ) ) );
                        while(distance<10&&count<6) {
                        In.putExtra("lati",lat);
                        In.putExtra("longi",lon);
                        In.putExtra("name",medicals);
                        startActivity(In);
                            count++;
                            break;
                        }
                        finish();

                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        map.put(TAG_PID, id);
                        map.put(TAG_NAME, name);

                        // adding HashList to ArrayList
                        productsList.add(map);
                    }
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
                    ListAdapter adapter = new SimpleAdapter(
                            AllProductsActivity.this, productsList,
                            R.layout.list_item, new String[]{TAG_PID,
                            TAG_NAME},
                            new int[]{R.id.pid, R.id.name});
                    // updating listview
                    setListAdapter(adapter);
                }
            }



            );


        }

    }
}

