package com.example.myclient;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
//import java.util.*;
import java.io.*;
import java.net.*;
import android.widget.TextView;
import android.content.Intent;

public class DisplayMessage extends ActionBarActivity {
	private static TextView textView;
	public static final String ipaddress = "192.168.0.104";
	public static final int port = 9090;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_message);
		//Intent helps you to switch to this activity from the parent activity
		Intent intent =getIntent();
		textView = (TextView) findViewById(R.id.textView1);
		try{
			tcpconnect();
		}catch(Exception e){
			System.out.println("Exception: "+e);
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_message, menu);
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
	
	public static void tcpconnect() throws IOException{
		Socket s=new Socket(ipaddress, port);
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			String answer = br.readLine();
			textView.setText(answer);
		}catch(Exception e){
			System.out.println("Exception: "+e);
		}finally{
			s.close();
		}
	}
	
	
}


