package com.example.gmap;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;



import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class SignActivity extends Activity {

	private Socket socket;

	private static final int SERVERPORT = 10021;
	private static final String SERVER_IP = "172.17.76.210";//"131.204.27.233";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_up);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sign, menu);
		new Thread(new ClientThread()).start();
		return true;
	}
	public void Sign_confirm(View v){
		//Check of the correction of info
    	EditText et1 = (EditText)findViewById(R.id.pds);
    	String pds = et1.getText().toString();
    	EditText et2 = (EditText)findViewById(R.id.pds_con);
    	String pds_con = et2.getText().toString();    	
    	Builder b = new Builder(this);
    	Toast toast1 = Toast.makeText(this, "Sign Up Successfully", Toast.LENGTH_SHORT);
    	//Toast toast2 = Toast.makeText(this, "Passwords Confirmed Incorrectly", Toast.LENGTH_SHORT);
    	if(pds.equals(pds_con)){
    	//collection of overall correct info of client
    		
    		//Name
    		EditText et_name = (EditText)findViewById(R.id.name);
        	String name = et_name.getText().toString();
        	String client_all_info = "1"+','+name;
        	
        	//Passwords
    		client_all_info = client_all_info +',' + pds;
    		
    		//gender
    		RadioButton m = (RadioButton)findViewById(R.id.m);
    		if(m.isChecked()){client_all_info = client_all_info +',' + "male";}
    		RadioButton f = (RadioButton)findViewById(R.id.f);
    		if(f.isChecked()){client_all_info = client_all_info +',' + "female";}
    		
    		//email
    		EditText et_email = (EditText)findViewById(R.id.email);
        	String email = et_email.getText().toString();
        	client_all_info = client_all_info +',' + email;
        	
        	//phone
        	EditText et_phone = (EditText)findViewById(R.id.phone);
        	String phone = et_phone.getText().toString();
        	client_all_info = client_all_info +',' + phone;
        	
        	//years
        	EditText et_year = (EditText)findViewById(R.id.years);
        	String years = et_year.getText().toString();
        	client_all_info = client_all_info +',' + years;
        	
        	//eng
        	RadioButton hi = (RadioButton)findViewById(R.id.hi);
    		if(hi.isChecked()){client_all_info = client_all_info +',' + "3";}
    		RadioButton mi = (RadioButton)findViewById(R.id.mi);
    		if(mi.isChecked()){client_all_info = client_all_info +',' + "2";}
    		RadioButton lo = (RadioButton)findViewById(R.id.lo);
    		if(lo.isChecked()){client_all_info = client_all_info +',' + "1";}
    		
    		//chn
    		RadioButton hi1 = (RadioButton)findViewById(R.id.hi1);
    		if(hi1.isChecked()){client_all_info = client_all_info +',' + "3";}
    		RadioButton mi1 = (RadioButton)findViewById(R.id.mi1);
    		if(mi1.isChecked()){client_all_info = client_all_info +',' + "2";}
    		RadioButton lo1 = (RadioButton)findViewById(R.id.lo1);
    		if(lo1.isChecked()){client_all_info = client_all_info +',' + "1";}
    		
    		//tag
    		CheckBox ins = (CheckBox)findViewById(R.id.ins);
    		CheckBox med = (CheckBox)findViewById(R.id.med);
    		CheckBox edu = (CheckBox)findViewById(R.id.edu);
    		CheckBox ten = (CheckBox)findViewById(R.id.ten);
    		CheckBox car = (CheckBox)findViewById(R.id.car);
    		CheckBox din = (CheckBox)findViewById(R.id.din);
    		CheckBox sho = (CheckBox)findViewById(R.id.sho);
    		if(ins.isChecked()){client_all_info = client_all_info +',' + '1';}
    		if(med.isChecked()){client_all_info = client_all_info +',' + '2';}
    		if(edu.isChecked()){client_all_info = client_all_info +',' + '3';}
    		if(ten.isChecked()){client_all_info = client_all_info +',' + '4';}
    		if(car.isChecked()){client_all_info = client_all_info +',' + '5';}
    		if(din.isChecked()){client_all_info = client_all_info +',' + '6';}
    		if(sho.isChecked()){client_all_info = client_all_info +',' + '7';}
    		
    		Intent i = new Intent(this, MainActivity.class);
    		
    		//add code to send the info to server
			try {
				PrintWriter out = new PrintWriter(new BufferedWriter(
						new OutputStreamWriter(socket.getOutputStream())),
						true);
				out.println(client_all_info);
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			toast1.show();
        	startActivity(i);
    		//b.setMessage("Sign Up Successfully");
    		//b.setNeutralButton("Thank you", null);
    		//AlertDialog ad = b.create();
    		//ad.show();
    	}
    	else{
    		//toast2.show();
    		//Intent i = new Intent(this, MainActivity.class);
        	//startActivity(i);
    		b.setMessage("Passwords Confirmed Incorrectly");
    		b.setNeutralButton("OK", null);
    		AlertDialog ad = b.create();
    		ad.show();
    	}
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
}
