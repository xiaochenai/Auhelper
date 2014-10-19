package com.example.gmap;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import com.example.gmap.LoginActivity.TestThread;



import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class DepActivity extends Activity {

	private Socket socket;
	// save response from server
	private String back = "-2";
	// save location information
	private double X=0.0, Y=0.0;
	private LocationManager mlocManager=null;
	private String provider;
	//port number
	private static final int SERVERPORT = 10021;
	//pay attention to IP address
	private static final String SERVER_IP = "172.17.76.110";//131.204.27.233
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.service_dsp);
		
		/* Use the LocationManager class to obtain GPS locations */
		mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		Criteria crta = new Criteria();
		crta.setAccuracy(Criteria.ACCURACY_FINE);
		crta.setAltitudeRequired(false);
		crta.setBearingRequired(false);
		crta.setCostAllowed(true);
		crta.setPowerRequirement(Criteria.POWER_LOW);
		provider = mlocManager.getBestProvider(crta, true);
		LocationListener mlocListener = new MyLocationListener();
		mlocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, mlocListener);
		 
	}
	//location manager
	public class MyLocationListener implements LocationListener
	{
		@Override
		public void onLocationChanged(Location loc)
		{
			X = loc.getLatitude();
			Y = loc.getLongitude();
			String Text = "My current location is: " + "Latitud = " + loc.getLatitude() + "Longitud = " + loc.getLongitude();
			Log.v("LOCATION", Text);
		}
		
		public Location getLocation(Location loc)
		{
			return loc;
		}
	
		@Override
		public void onProviderDisabled(String provider)
		{
			Toast.makeText( getApplicationContext(), "Gps Disabled", Toast.LENGTH_SHORT ).show();
		}
	
		@Override
		public void onProviderEnabled(String provider)
		{
			Toast.makeText( getApplicationContext(), "Gps Enabled", Toast.LENGTH_SHORT).show();
		}
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras)
		{
		}

	}/* End of Class MyLocationListener */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dep, menu);
		return true;
	}
	public void Sign_confirm(View v){    
		//getLocation
		Location location = mlocManager.getLastKnownLocation(provider);
		X = location.getLatitude();
		Y = location.getLongitude();
		
    		//Description
    		EditText et_name = (EditText)findViewById(R.id.name);
        	String name = et_name.getText().toString();
        	String client_all_info = "3"+','+name;

    		//tag
    		CheckBox ins = (CheckBox)findViewById(R.id.ins1);
    		CheckBox med = (CheckBox)findViewById(R.id.med1);
    		CheckBox edu = (CheckBox)findViewById(R.id.edu1);
    		CheckBox ten = (CheckBox)findViewById(R.id.ten1);
    		CheckBox car = (CheckBox)findViewById(R.id.car1);
    		CheckBox din = (CheckBox)findViewById(R.id.din1);
    		CheckBox sho = (CheckBox)findViewById(R.id.sho1);
    		if(ins.isChecked()){client_all_info = client_all_info +',' + '1';}
    		if(med.isChecked()){client_all_info = client_all_info +',' + '2';}
    		if(edu.isChecked()){client_all_info = client_all_info +',' + '3';}
    		if(ten.isChecked()){client_all_info = client_all_info +',' + '4';}
    		if(car.isChecked()){client_all_info = client_all_info +',' + '5';}
    		if(din.isChecked()){client_all_info = client_all_info +',' + '6';}
    		if(sho.isChecked()){client_all_info = client_all_info +',' + '7';}
    		//get user ID
    		Bundle extras = getIntent().getExtras();
    		if (extras != null) {
    		    String value = extras.getString("User_ID");
    		    client_all_info = client_all_info +','+ String.valueOf(X) +','+String.valueOf(Y)+',' + value;
    		}
		//start a test thread to send a request to server
    		new Thread(new TestThread(client_all_info)).start();
    	}
	
	class TestThread implements Runnable {
		String sentence;
		DepActivity la;
		TestThread(String s) {
			sentence = s;
		}
		@Override
		public void run() {
			try {
				//start socket programming
				Socket clientSocket = new Socket(SERVER_IP, SERVERPORT);//172.17.36.85
				DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
				BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				//send sentence to server
				outToServer.writeBytes(sentence + '\n');
				//save response from server to "back"  
				back = inFromServer.readLine();
				//start map activity
				Intent i = new Intent(la, MapActivity.class);
				//pass response to map
				i.putExtra("Input", String.valueOf(back));
				startActivity(i);        
	            		clientSocket.close();
			}
			catch (IOException e)
			{
				System.out.println("An Error Occurs!");
			}
		}

	}
}
