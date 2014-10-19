/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.gmap;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.http.util.EncodingUtils;

import com.example.gmap.DepActivity.TestThread;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;





import android.R.integer;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.TextView;
import android.widget.Toast;
/**
 * This demo shows how GMS Location can be used to check for changes to the users location.  The
 * "My Location" button uses GMS Location to set the blue dot representing the users location. To
 * track changes to the users location on the map, we request updates from the
 * {@link LocationClient}.
 */
public class MapActivity extends FragmentActivity
        implements
        ConnectionCallbacks,
        OnConnectionFailedListener,
        LocationListener,
        OnMyLocationButtonClickListener, OnInfoWindowClickListener {

    private GoogleMap mMap;
    static final LatLng PERTH = new LatLng(32.609235, -85.48);
    private LocationClient mLocationClient;
    private TextView mMessageView;
    private ServerSocket serverSocket;
    private static final int SERVERPORT = 10021;
	private static final String SERVER_IP = "172.17.76.210";
    private String str;
    private Socket socket = null;
    private String messageString;
    private String[] nameStrings;
    private String[] phoneStrings;
    private String[] langtitudeStrings={"32","35"};
    private String[] longtitudeStrings={"-80","-75"};
    private ProgressDialog pd;
    private double OldLantitude=0;
    private double OldLongtitude=0;
    Thread serverThread = null;
    private String UserID;
    // These settings are the same as the settings for the map. They will in fact give you updates
    // at the maximal rates currently possible.
    private static final LocationRequest REQUEST = LocationRequest.create()
            .setInterval(5000)         // 5 seconds
            .setFastestInterval(10)    // 16ms = 60fps
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    /**********
     * 
     * @param data: the received data from server
     * note: now the received data of lantitude and longtitude is not correct so I have commented it and use fixed values
     */
    private void ProcessData(String data){
    	UserID = data.split(":")[1];
    	data = data.split(":")[1];
    	Log.v("UserID", UserID);
    	String[] tempStrings = data.split(",");
    	messageString = tempStrings[0];
    	int i = tempStrings.length/4;
    	Log.v("test", "length " + tempStrings.length);
    	Log.v("test", "i is " + i);
    	System.out.println("i is : " + i);
    	nameStrings = new String[i];
    	phoneStrings = new String[i];
//    	langtitudeStrings = new String[i]; // if the received data of long and lant is correct uncomments this two lines 
//    	longtitudeStrings = new String[i];
    	for(int j=0;j<i;j++){
    		nameStrings[j] = tempStrings[j*4 + 1];
    		phoneStrings[j] = tempStrings[j*4 + 2];
    		//langtitudeStrings[j] = tempStrings[j/4 + 3];// same with privious
    		//longtitudeStrings[j] = tempStrings[j/4 + 4];
    	}
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    String value = extras.getString("Input");
		    Log.v("input string", value);
		    ProcessData(value);
		}
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mMessageView = (TextView) findViewById(R.id.message_text);
        //new Thread(new ClientThread()).start();
		try {
		//	ClientSide clientSide=new ClientSide("162.209.109.193",10021);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }




    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        setUpLocationClientIfNeeded();
        mLocationClient.connect();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mLocationClient != null) {
            mLocationClient.disconnect();
        }
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                mMap.setMyLocationEnabled(true);
                mMap.setOnMyLocationButtonClickListener(this);
                mMap.setOnInfoWindowClickListener(this);
               // mMap.setOnMyLocationButtonClickListener(mOnMyLocationButtonClickListener);
            }
        }
    }
    /*************
     * response to the MyLocationButton click event, we can add some functions here
     */
//    private final OnMyLocationButtonClickListener mOnMyLocationButtonClickListener = new
//            OnMyLocationButtonClickListener() {
//        @Override
//        public boolean onMyLocationButtonClick() {
//        	System.out.println("&&&&&&&&&&&&&&&&&&&&&");
//        	
//            if (mLocationClient.isConnected() && mLocationClient.getLastLocation() == null) {
//                System.out.println("******************");
//                return true;
//            }
//            return false;
//        }
//    };
    /******
     * show all markers we processed
     */
    private void showMarker(){
    	
    	Log.v("test", "length : " + nameStrings.length);
    	for(int i=0;i<nameStrings.length;i++){
    		Log.v("test name", "Name : " + nameStrings[i]);
    		Log.v("test phone", "Phone : " + phoneStrings[i]);
    		double Lan = Double.parseDouble(langtitudeStrings[i]);
    		double longt = Double.parseDouble(longtitudeStrings[i]);
    		Log.v("test", "lan" + Lan + "long" + longt);
    		Marker melbourne = mMap.addMarker(new MarkerOptions()
    		.position(new LatLng(Lan,longt))
    		.title(nameStrings[i])
    		.snippet(phoneStrings[i]));
    		melbourne.getSnippet();
    	}
    }


    private void setUpLocationClientIfNeeded() {
        if (mLocationClient == null) {
            mLocationClient = new LocationClient(
                    getApplicationContext(),
                    this,  // ConnectionCallbacks
                    this); // OnConnectionFailedListener
        }
    }

    /**
     * Button to get current Location. This demonstrates how to get the current Location as required
     * without needing to register a LocationListener.
     */
    public void showMyLocation(View view) {
        if (mLocationClient != null && mLocationClient.isConnected()) {
            String msg = "Location = " + mLocationClient.getLastLocation();
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            

        }
    }

    /**
     * Implementation of {@link LocationListener}.
     * when locations changed we send new location data to server.
     * still exist some problem, may send null to server
     */
    @Override
    public void onLocationChanged(Location location) {
        mMessageView.setText("Location = " + location.getLatitude() + location.getLongitude() + location.getSpeed());
        this.str = location.getLatitude() + "," + location.getLongitude();
        Position.position=this.str;
        ClientThread ctClientThread = new ClientThread();
        new Thread(ctClientThread).start();
        double Distance = GetDistance(location.getLatitude(), location.getLongitude(), OldLantitude, OldLongtitude);
        if(Distance > 0.0000000001){
        Log.v("Distance ", Distance+"");
        	OldLantitude = location.getLatitude();
        	OldLongtitude = location.getLongitude();
        	String data2Server = "4,"+UserID+","+OldLantitude+"," + OldLongtitude;
        	Log.v("Distance", "1234");
        	new Thread(new TestThread(data2Server)).start();
        }
        


    }
    private double rad(double d)
    {
        return d * Math.PI / 180.0;
    }
    private double GetDistance(double lat1, double lng1, double lat2, double lng2)
    {
            double radLat1 = rad(lat1);
            double radLat2 = rad(lat2);
            double a = radLat1 - radLat2;
            double b = rad(lng1) - rad(lng2);
            double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) + Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
            s = s * 6378.137;
            s = Math.round(s * 10000) / 10000;
            return s;
    }
	
    /**
     * Callback called when connected to GCore. Implementation of {@link ConnectionCallbacks}.
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        mLocationClient.requestLocationUpdates(
                REQUEST,
                this);  // LocationListener
    }

    /**
     * Callback called when disconnected from GCore. Implementation of {@link ConnectionCallbacks}.
     */
    @Override
    public void onDisconnected() {
        // Do nothing
    }

    /**
     * Implementation of {@link OnConnectionFailedListener}.
     */
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Do nothing
    }

    @Override
    public boolean onMyLocationButtonClick() {

        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
		try {
				 showMarker();
				 Toast.makeText(this, "return back value", Toast.LENGTH_SHORT).show();	
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

        return false;
    }

    public void writeFile(String fileName,String writestr) throws IOException{ 
      try{ 

            FileOutputStream fout =openFileOutput(fileName, MODE_PRIVATE); 

            byte [] bytes = writestr.getBytes(); 

            fout.write(bytes); 

            fout.close(); 
          } 

            catch(Exception e){ 
            e.printStackTrace(); 
           } 
    } 

    //�����
    public String readFile(String fileName) throws IOException{ 
      String res=""; 
      try{ 
             FileInputStream fin = openFileInput(fileName); 
             int length = fin.available(); 
             byte [] buffer = new byte[length]; 
             fin.read(buffer);     
             res = EncodingUtils.getString(buffer, "UTF-8"); 
             fin.close();     
         } 
         catch(Exception e){ 
             e.printStackTrace(); 
         } 
         return res; 

    }   
    public void onInfoWindowClick(Marker marker) {
    	
    	String snippetString = marker.getSnippet().replace("snippet", "");
        String titleString = marker.getTitle().replace("title", "");
        System.out.println("title" + titleString);
        System.out.println("snippet" + snippetString);
        String[] infoarrayStrings = snippetString.split("</br>");
        System.out.println("PHONE IS  :  " + infoarrayStrings[2]);
        String mobileString = infoarrayStrings[2];
        String messageString = "If you have time would you mind to give me a hand?";
        if (messageString != null) {

			SmsManager sms = SmsManager.getDefault();
			List<String> texts = sms.divideMessage(messageString);
			for (String text : texts) {
				sms.sendTextMessage(mobileString, null, text, null, null);
				}
		}
        pd = new ProgressDialog(MapActivity.this);
		pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pd.setTitle("����״̬");
		pd.setMessage("���ڷ��ͣ����Ժ�");

		pd.setIndeterminate(true);
		pd.setCancelable(true);

		pd.show();
        		
    }
    class ClientThread implements Runnable {

		@Override
		public void run() {
			
			try {
				InetAddress serverAddr = InetAddress.getByName(SERVER_IP);

				socket = new Socket(serverAddr, SERVERPORT);

			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}

	}
	class TestThread implements Runnable {
		String sentence;
		TestThread(String s) {
			sentence = s;

		}
		@Override
		public void run() {
			System.out.print("Update Location!");
			String back;
			try {
	            //String modifiedSentence;
	            //java Client "131.204.27.233" 10021 "2,xiao,123456"
	            //System.out.println(args[0]+Integer.decode(args[1]));
				System.out.print("update location : MapActivity!");
	            Socket clientSocket = new Socket(SERVER_IP, SERVERPORT);//172.17.36.85
	            Log.v("test", "OR HERE_map!!");
	            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
	            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	            outToServer.writeBytes(sentence + '\n');
	            Log.v("test", sentence);
	            back = inFromServer.readLine();
	            Log.v("test123", back);
	            System.out.println("FROM SERVER: " + back);
	            back = back;
				//Toast toast1 = Toast.makeText(this, "Login In Successfully", Toast.LENGTH_SHORT);
				//toast1.show();   
	            clientSocket.close();
	            Log.v("MAP From Server", back);
	            Log.v("test", "Go to Map SUCCESS");
	            
			}
			catch (IOException e)
			{
				System.out.println("An Error Occurs!");
			}
		}

	}
}
