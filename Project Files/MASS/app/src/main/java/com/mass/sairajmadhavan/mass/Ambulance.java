package com.mass.sairajmadhavan.mass;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;


public class Ambulance extends Activity {

    EditText textOut;
    TextView textIn;
    GPSTracker gps;
    double latitude;
    double longitude;
String ipaddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance);
        textOut = (EditText) findViewById(R.id.textout);
        Button buttonSend = (Button) findViewById(R.id.send);
        textIn = (TextView) findViewById(R.id.textin);
        buttonSend.setOnClickListener(buttonSendOnClickListener);
    }


    Button.OnClickListener buttonSendOnClickListener
            = new Button.OnClickListener() {

        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            ipaddress=textOut.getText().toString();
            Socket socket = null;
            DataOutputStream dataOutputStream = null;
            DataInputStream dataInputStream = null;
            DataOutputStream dataOutputStream2=null;
            gps = new GPSTracker(Ambulance.this);
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
            try {
                socket = new Socket(ipaddress, 8889);
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream2=new DataOutputStream(socket.getOutputStream());
                //dataOutputStream.writeUTF(textOut.getText().toString());
                dataOutputStream.writeDouble(latitude);
                dataOutputStream2.writeDouble(longitude);
                textIn.setText(dataInputStream.readUTF());
            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            if (dataOutputStream != null){
                try {
                    dataOutputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            if (dataInputStream != null){
                try {
                    dataInputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }


    };
}
