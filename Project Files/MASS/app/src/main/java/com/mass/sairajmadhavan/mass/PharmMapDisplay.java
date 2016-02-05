package com.mass.sairajmadhavan.mass;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
/**
 * Created by durairajan on 14-03-2015.
 */

public class PharmMapDisplay extends FragmentActivity {
    private GoogleMap googleMap;
    GPSTracker gps;
    double latitude;
    double longitude;
    double mappoint;
    String name;
    String address="";
    String phno="";
    double radius;
    String types[];
    Location location = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pharmdisplay);
        Bundle b = getIntent().getExtras();

            latitude = b.getDouble("lati");
            longitude = b.getDouble("longi");
        name=b.getString("name");
        address=b.getString("address");
        phno=b.getString("phno");
            //mappoint=b.getDouble("LatLng");


        try
        {
            // Loading map
            initilizeMap();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }
    /**
     * function to load map. If map is not created it will create it for you
     * */
    private void initilizeMap()
    {
        if (googleMap == null)
        {
            googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            googleMap.getUiSettings().setZoomControlsEnabled(true);

            MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title(""+name+"\n"+address+"\n"+phno);
            googleMap.addMarker(marker);
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);

            CameraPosition cameraPosition = new CameraPosition.Builder().target(
                    new LatLng(latitude, longitude)).zoom(18).build();

            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            /*LocationManager  locMgr  = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location recentLoc = locMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            double lat = recentLoc.getLatitude();
            double lon = recentLoc.getLongitude();
            String geoURI = String.format("geo:%f,%f?q=hospital", latitude, longitude);
            Uri geo = Uri.parse(geoURI);
            Intent geoMap = new Intent(Intent.ACTION_VIEW, geo);
            startActivity(geoMap);*/
            // check if map is created successfully or not

            if (googleMap == null)
            {
                Toast.makeText(getApplicationContext(),"Sorry! unable to create maps", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        //initilizeMap();
    }

}
