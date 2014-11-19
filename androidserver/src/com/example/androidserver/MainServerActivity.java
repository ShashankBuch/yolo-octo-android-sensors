package com.example.androidserver;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import java.util.*;
import java.io.*;
import java.net.*;

//port com.example.myclient.R;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainServerActivity extends ActionBarActivity{
	public String str;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_server);
		try{
			str=serverfunc();
			if(str.equalsIgnoreCase("getlist")){
				//get list of sensors
				Intent intent=new Intent(this, ServerListActivity.class);
				startActivity(intent);
			}
			else if(str.equalsIgnoreCase("getlightreading")){
				//get light reading
				Intent intent=new Intent(this, ServerLightActivity.class);
				startActivity(intent);
			}
			
			else if(str.equalsIgnoreCase("getlocation")){
				//get location coordinates
				Intent intent=new Intent(this, ServerLocationActivity.class);
				startActivity(intent);
			}
			
		}catch(Exception e){
			System.out.println("Exception: "+e);
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_server, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	//this function will receive the messages from the client and process the appropriate result
	public static String serverfunc() throws IOException, UnknownHostException{
		String in;
		ServerSocket ss=new ServerSocket(9090);
		try{
			while(true){
				Socket s= ss.accept();
				try{
					//bufferedreader is used to accept strings send by the client.
					BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
					in = br.readLine();
					return in;
					//the following control flow structure sends the appropriate result back to the client
					
					/*if(in.equalsIgnoreCase("getsensorlist")){
						PrintWriter out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream())));
						out.println(list);
						out.flush();
					}
					
					else if(in.equalsIgnoreCase("getlightreading")){
						//get light reading
					}
					
					else if(in.equalsIgnoreCase("getlocation")){
						//get location coordinates
					}*/
						
				}catch(Exception e){
					System.out.println("Exception: "+e);
					} finally{
						s.close();
				}
					
				}
			} finally{
			ss.close();
		}
		
	 }
}


