package com.example.androidserver;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.*;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;
import android.widget.Toast;

 


public class ServerLightActivity extends ActionBarActivity {
	public static final int port =9090;
	TextView textReading;
	String reading;
	public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_server_light);
	        
	        textReading = (TextView)findViewById(R.id.textView1);
	        
	         
	        SensorManager sensorManager 
	        = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
	        Sensor lightSensor 
	        = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
	      
	        if (lightSensor == null){
	         Toast.makeText(ServerLightActivity.this, 
	           "No Light Sensor! quit.", 
	           Toast.LENGTH_LONG).show();
	        }else{
	         
	         sensorManager.registerListener(lightSensorEventListener, 
	           lightSensor, 
	           SensorManager.SENSOR_DELAY_NORMAL);
	          
	        }
	    }
	     
	    SensorEventListener lightSensorEventListener
	    = new SensorEventListener(){
	 
	  @Override
	  public void onAccuracyChanged(Sensor sensor, int accuracy) {
	   // TODO Auto-generated method stub
	    
	  }
	 
	  @Override
	  public void onSensorChanged(SensorEvent event) {
	   // TODO Auto-generated method stub
	   if(event.sensor.getType()==Sensor.TYPE_LIGHT){
	    float currentReading = event.values[0];
	    //lightMeter.setProgress((int)currentReading);
	    textReading.setText("Current Reading: " + String.valueOf(currentReading));
	    reading = textReading.getText().toString();
	    try{
	    	sendmessage(reading);
	    }catch(Exception e){
	    	System.out.println("Exception: "+e);
	    }
	    
	   }
	  }
	      
	    };

	public static void sendmessage(String str) throws IOException{
		String s =str;
		ServerSocket ss=new ServerSocket(port);
		try{
			while(true){
				Socket socket=ss.accept();
				try{
					PrintWriter out=new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
					out.println(s);
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
		}finally{
			ss.close();
		}
	}
    
}