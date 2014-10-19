package com.example.gmap;

  
import java.io.BufferedReader;  
import java.io.InputStreamReader;  
import java.io.PrintWriter;  
import java.net.InetAddress;  
import java.net.Socket;  
import java.net.UnknownHostException;
  
import java.io.BufferedReader;  
import java.io.IOException;  
import java.io.InputStreamReader;  
import java.io.PrintWriter;  
import java.net.ServerSocket;  
import java.net.Socket;  
  
import java.io.BufferedReader;  
import java.io.InputStreamReader;  
import java.io.PrintWriter;  
import java.net.InetAddress;  
import java.net.Socket;  

import com.google.android.gms.internal.s;
  
   public class MyClient {  
    static Socket server; 
    String receiveinfor;
    String sendinfor;
    
    public MyClient(String sendInfor) throws Exception {
		// TODO Auto-generated constructor stub
            sendinfor=sendInfor;
			startnewconnection();
			;
        
	}
  
    void startnewconnection() throws Exception{
    	PrintWriter out;
		BufferedReader	in;
    	new Thread(new MyThreadedClass()).start();
    }
    /*	System.out.println("startnewconnection()");
        server = new Socket("172.17.4.111", 10021);
        System.out.println("2222222222222222");
        
        
        BufferedReader in = new BufferedReader(new InputStreamReader(  
                server.getInputStream()));  
        
        PrintWriter out = new PrintWriter(server.getOutputStream());  
        System.out.println("111111111111111");
        BufferedReader wt = new BufferedReader(new InputStreamReader(System.in)); 
        
       // String str = wt.readLine();  
        String str=sendinfor;
        out.println(str);  
        out.flush();  
        receiveinfor=in.readLine();
        System.out.println(receiveinfor);  
        
        server.close();  
        System.out.println("server.close()");
    	*/
    	
    	class MyThreadedClass implements Runnable {
    		PrintWriter out=null;
    		BufferedReader	in=null;
    		   public void run ()
    		   {
    		      // put your thread code here
    			   
    			   System.out.println("startnewconnection()");
    			   try {
					server = new Socket("162.209.109.193", 10020);
					System.out.println("1111");
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
    		        
    		       // BufferedReader in;
					try {
						System.out.println("2222");
						in = new BufferedReader(new InputStreamReader(  
						        server.getInputStream()));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  
    		        
    		      //  PrintWriter out;
					try {
						System.out.println("2222");
						out = new PrintWriter(server.getOutputStream());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  
    		        
    		        BufferedReader wt = new BufferedReader(new InputStreamReader(System.in)); 
    		        
    		       // String str = wt.readLine();  
    		        String str=sendinfor;
    		        
    		        out.println(str);  
    		        out.flush();  
    		        
    		        try {
						receiveinfor=in.readLine();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    		        System.out.println(receiveinfor);  
    		        try {
						server.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  
    		   }
    		
    }
    
   
}  
