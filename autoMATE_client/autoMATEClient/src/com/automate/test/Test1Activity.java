package com.automate.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import com.automate.R;
import com.automate.app.AutoMATEApplication;
import com.automate.app.route.Action;
import com.automate.app.route.Route;
import com.automate.app.route.RouteController;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class Test1Activity extends Activity {

	private static String SERVER_ADDRESS = "10.11.106.136";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		new Thread(new Runnable() {
			@Override
			public void run() {
				for(int i = 0; i < 1000; ++i) {
					try {
						Socket socket = new Socket(SERVER_ADDRESS, 6300);
						PrintWriter writer = new PrintWriter(socket.getOutputStream());
						BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						writer.println("register");
						writer.flush();
						int id = Integer.parseInt(reader.readLine());
						socket.close();
					} catch (UnknownHostException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				try {
					ServerSocket listenSocket = new ServerSocket(6300);
					while(true) {
						final Socket inputSocket = listenSocket.accept();
						new Thread(new Runnable() {
							@Override
							public void run() {
								BufferedReader reader;
								try {
									reader = new BufferedReader(new InputStreamReader(inputSocket.getInputStream()));
									String message = reader.readLine();
									if(message == null) return;
									if(message.startsWith("ping")) {
										Socket socket = new Socket(SERVER_ADDRESS, 6300);
										PrintWriter writer = new PrintWriter(socket.getOutputStream());
										writer.write("ping " + message.substring(5));
										writer.flush();
										socket.close();
									}
									inputSocket.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}).start();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}


	public void onPressMePressed(View v) {
	}
}
