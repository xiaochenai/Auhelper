/*
	This is just a test file. It doesn't make any difference for the project.
*/

package com.example.gmap;

import java.io.*;
import java.net.*;

public class ConnectServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
        String sentence;
        String modifiedSentence;
        //java Client "131.204.27.233" 10021 "2,xiao,123456"
        //System.out.println(args[0]+Integer.decode(args[1]));
        Socket clientSocket = new Socket("172.17.76.110", Integer.decode("10021"));//172.17.36.85

        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));


        sentence = "2, xiao, 123456";//"2, HelloAuburn, 123456, male, bcd2341@aubrn.edu, 3345564534, 5.5, 2, 3, 3, 4 ,5";//inFromUser.readLine();

        sentence = sentence.replace('\n','\0');

        outToServer.writeBytes(sentence + '\n');
        modifiedSentence = inFromServer.readLine();
        System.out.println("FROM SERVER: " + modifiedSentence);
        clientSocket.close();

	}

}
