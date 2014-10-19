package com.example.gmap;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.http.util.EncodingUtils;

public class ServerThread extends Thread{
	private int port;
	private String clientSentence;
    private String capitalizedSentence;
    private ServerSocket welcomeSocket=null;
	public ServerThread(int port) throws IOException{
		this.port = port;
		 welcomeSocket = new ServerSocket(port);

	}
	public void run(){
		while(true){
			System.out.println("waiting for packets");
			Socket connectionSocket;
			try {
				connectionSocket = welcomeSocket.accept();
				BufferedReader inFromClient =
			               new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			            clientSentence = inFromClient.readLine();
			            System.out.println("Received: " + clientSentence);
			            capitalizedSentence = readFile("location.txt");
			            
			            capitalizedSentence = capitalizedSentence + '\n';
			            System.out.println(capitalizedSentence);
			            outToClient.writeBytes(capitalizedSentence);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
		
		}
	}

	

	//¶ÁÊý¾Ý
	public String readFile(String fileName) throws IOException{ 
	  String res=""; 
	  try{ 
	         FileInputStream fin = new FileInputStream(fileName); 
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

}
