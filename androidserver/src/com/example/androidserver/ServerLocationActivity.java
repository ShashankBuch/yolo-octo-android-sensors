package com.example.androidserver;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import android.widget.TextView;
import android.widget.ProgressBar;


public class ServerLocationActivity extends Activity implements OnClickListener, android.content.DialogInterface.OnClickListener {

    private TextView editTextShowLocation;
    private Button buttonGetLocation;
    private ProgressBar progress;
    

    private LocationManager locManager;
    private LocationListener locListener = new MyLocationListener();

    private boolean gps_enabled = false;
    private boolean network_enabled = false;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_location);
        editTextShowLocation = (TextView) findViewById(R.id.textView1);
        
        progress = (ProgressBar) findViewById(R.id.progressBar);
        progress.setVisibility(View.GONE);

        buttonGetLocation = (Button) findViewById(R.id.button);
        buttonGetLocation.setOnClickListener(this);

        locManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public void onClick(View v) {
        progress.setVisibility(View.VISIBLE);
        // exceptions will be thrown if provider is not permitted.
        try {
            gps_enabled = locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }
        try {
            network_enabled = locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        // don't start listeners if no provider is enabled
        if (!gps_enabled && !network_enabled) {
            AlertDialog.Builder builder = new Builder(this);
            builder.setTitle("Attention!");
            builder.setMessage("Sorry, location is not determined. Please enable location providers");
            builder.setPositiveButton("OK", this);
            builder.setNeutralButton("Cancel", this);
            builder.create().show();
            progress.setVisibility(View.GONE);
        }

        if (gps_enabled) {
            locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locListener);
        }
        if (network_enabled) {
            locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locListener);
        }
    }

    class MyLocationListener implements LocationListener {
    	public String loc;
    	public static final int port = 9090;
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                // This needs to stop getting the location data and save the battery power.
                locManager.removeUpdates(locListener); 

                String longitude = "Longitude: " + location.getLongitude();
                String latitude = "Latitude: " + location.getLatitude();
                

                editTextShowLocation.setText(longitude + "\n" + latitude + "\n");
                progress.setVisibility(View.GONE);
                loc = editTextShowLocation.getText().toString();
                
                ServerSocket ss = null;
                try{
                	ss=new ServerSocket(port);
                	while(true){
        				Socket socket=ss.accept();
        				try{
        					PrintWriter out=new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        					out.println(loc);
        					out.flush();
        					out.close();
        				}catch(Exception e){
        					System.out.println("Exception: "+e);
        				}finally{
        					socket.close();
        				}
        			}
                }catch(Exception e){
                	System.out.println("Exception: "+e);
                }/*finally{
                	ss.close();
                }*/
               
            }
        }
        
        @Override
        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // TODO Auto-generated method stub

        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if(which == DialogInterface.BUTTON_NEUTRAL){
            editTextShowLocation.setText("Sorry, location is not determined. To fix this please enable location providers");
        }else if (which == DialogInterface.BUTTON_POSITIVE) {
            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        }
    }

}