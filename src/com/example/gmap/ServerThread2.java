package com.example.gmap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;


public class ServerThread2 implements Runnable{
	private ServerSocket serverSocket;
	Thread serverThread = null;
	public void run() {
		Socket socket = null;
		try {
			serverSocket = new ServerSocket(10021);
		} catch (IOException e) {
			e.printStackTrace();
		}
		while (!Thread.currentThread().isInterrupted()) {

			try {

				socket = serverSocket.accept();

				CommunicationThread commThread = new CommunicationThread(socket);
				new Thread(commThread).start();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	class CommunicationThread implements Runnable {

		private Socket clientSocket;

		private BufferedReader input;

		public CommunicationThread(Socket clientSocket) {

			this.clientSocket = clientSocket;

			try {

				this.input = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void run() {
			

			while (!Thread.currentThread().isInterrupted()) {
					System.out.println("Thread Running");
				try {

					String read = input.readLine();
					System.out.println("READINPUT : " + read);

					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
