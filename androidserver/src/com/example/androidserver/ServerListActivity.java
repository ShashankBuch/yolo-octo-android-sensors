package com.example.androidserver;

import android.support.v7.app.ActionBarActivity;

import java.io.*;
import java.net.*;
import java.util.*;
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class ServerListActivity extends ActionBarActivity implements SensorEventListener {
	private SensorManager mSensorManager;
	private TextView mTextView;
	public static final String ipaddress = "192.168.0.104";
	public static final int port = 9090;
	String list="";
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_server_list);
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		mTextView = (TextView) findViewById(R.id.textView1);

		List<Sensor> mList= mSensorManager.getSensorList(Sensor.TYPE_ALL);
		for (int i = 1; i < mList.size(); i++) {
			mTextView.append("\n" + mList.get(i).getName());
			}
			list = mTextView.getText().toString();
			try{
				sendmessage(list);
			}catch(Exception e){
				System.out.println("Exception: "+e);
			}
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub 

	}
	protected void onPause(){
		super.onStop();
	}
	protected void onStop() {
		super.onStop();
	}
	protected void onResume()
	{
		super.onResume();
	}

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

