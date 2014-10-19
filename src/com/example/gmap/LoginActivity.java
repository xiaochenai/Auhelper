package com.example.gmap;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.*;
import java.net.*;



import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private Socket socket = null;
	// save response from server
	private String back = "-2";
	//port number
	private static final int SERVERPORT = 10021;
	//pay attention to IP address
	private static final String SERVER_IP = "172.17.76.110";//131.204.27.233
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.log_in);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	public void login_confirm(View v){   	
	    	//Name
	    	EditText et1 = (EditText)findViewById(R.id.name1);
	    	String nam = et1.getText().toString();
	    	String client_all_info = "2"+','+nam;
	    	
	    	//Passwords
	    	EditText et2 = (EditText)findViewById(R.id.pds1);
	    	String pds = et2.getText().toString(); 
		client_all_info = client_all_info +',' + pds;
		Log.v("test", "really!");			
		//start a new thread to check whether it is a valid user or not
		new Thread(new TestThread(client_all_info)).start();

	}
	
	class TestThread implements Runnable {
		String sentence;
		LoginActivity la;
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
				clientSocket.close();
	            
				if(back.equals("-1")){
					//invalid user, return to main activity
					Intent i = new Intent(la, MainActivity.class);
					startActivity(i);
				}
				else{
					//valid user. go to request activity and pass USER_ID
					Intent i = new Intent(la, DepActivity.class);
					i.putExtra("User_ID", String.valueOf(back));
					startActivity(i);
				}
			}
			catch (IOException e)
			{
				System.out.println("An Error Occurs!");
			}
		}

	}
}

