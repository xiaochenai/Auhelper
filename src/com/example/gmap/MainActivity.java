package com.example.gmap;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_choice);

    }


    
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    public void Sign_up(View v){
    	Intent i = new Intent(this, SignActivity.class);
    	startActivity(i);
    }
    public void Log_in(View v){
    	Intent i = new Intent(this, LoginActivity.class);
    	startActivity(i);
    }
}
