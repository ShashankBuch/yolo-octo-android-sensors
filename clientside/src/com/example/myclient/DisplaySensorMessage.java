package com.example.myclient;

import android.support.v7.app.ActionBarActivity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import java.util.List;



public class DisplaySensorMessage extends ActionBarActivity implements SensorEventListener {
	private SensorManager mSensorManager;
	private TextView mTextView;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_sensor_message);
	// Get the message from the intent
	Intent intent = getIntent();
    String msg = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
	
	mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
	mTextView = (TextView) findViewById(R.id.textView1);
	if(msg.equalsIgnoreCase("getsensorlist")){
	List<Sensor> mList= mSensorManager.getSensorList(Sensor.TYPE_ALL);
	for (int i = 1; i < mList.size(); i++) {
		mTextView.append("\n" + mList.get(i).getName());
		}
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

	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_sensor_message, menu);
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
}
