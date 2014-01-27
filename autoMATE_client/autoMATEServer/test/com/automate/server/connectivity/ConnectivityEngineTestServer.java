package com.automate.server.connectivity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import com.automate.server.connectivity.ClientId;
import com.automate.server.connectivity.ConnectivityEngine;
import com.automate.server.connectivity.EngineCallback;
import com.automate.server.connectivity.ReadThread.Callback;


public class ConnectivityEngineTestServer {

	private static Map<String, String> clients = new HashMap<String, String>();
	private static Map<String, Void> pinged = new HashMap<String, Void>();

	private static int nextId = 0;

	private static ArrayList<Long> failures = new ArrayList<Long>();

	private static long timeOfLastPing;
	private static int pings;
	private static long totalPingTime;

	private static Callback callback;

	private static long start;

	public static void main(String[] args) {

		EngineCallback callback = new EngineCallback() {

			@Override
			public void pingAllClients(final ClientPingListener listener) {
				long time = System.currentTimeMillis();
				if(timeOfLastPing != 0) {
					totalPingTime += time - timeOfLastPing;
				}
				timeOfLastPing = time;
				synchronized(clients) {
					for(final Entry<String,String>client : clients.entrySet()) {
						synchronized (pinged) {
							if(!pinged.containsKey(client.getKey())) {
								//System.out.println("Pinging client " + client.getKey());
								pinged.put(client.getKey(), (Void)null);
								new Thread(new Runnable() {
									@Override
									public void run() {
										try {
											Socket socket = new Socket(client.getValue(), 6300);
											socket.getOutputStream().write(("ping " + client.getKey()).getBytes());
											socket.close();
										} catch (IOException e) {
											connectionLost(new ClientId(ClientType.APP, client.getKey()));
										}
										listener.clientAdded(new ClientId(ClientType.APP, client.getKey()));
									}
								}).start();
							} else {
								System.out.println("Skipping client " + client.getKey());
							}
						}
					}
				}
				++pings;
			}

			@Override
			public void connectionLost(ClientId clientId) {
				System.out.println("Removing client " + clientId.uid);
				synchronized (failures) {
					failures.add(System.currentTimeMillis() - start);
				}
				synchronized(clients) {
					clients.remove(clientId.uid);
				}
			}
		};

		final ConnectivityEngine engine = new ConnectivityEngine(15,5, callback);
		ConnectivityEngineTestServer.callback = new Callback() {
			@Override
			public int registerSocket(Socket socket) {
				InetAddress address = socket.getInetAddress();
				String host = address.getHostAddress();
				int id;
				synchronized (clients) {
					id = nextId++;
				}
				try {
					socket.getOutputStream().write(String.valueOf(id).getBytes());
					synchronized(clients) {
						clients.put(String.valueOf(id), host);
					}
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return id;
			}

			@Override
			public void onPingRecieved(ClientId client) {
				engine.ackPingReceivedFromClient(client);
				synchronized (pinged) {
					pinged.remove(client.uid);
				}
			}
		};
		startListener(engine);
		try {
			System.out.println("Press any key to continue.");
			new BufferedReader(new InputStreamReader(System.in)).read();
			System.out.println("Starting test server.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		start = System.currentTimeMillis();
		engine.start();
		synchronized (failures) {
			try {
				failures.wait(600000);
			} catch (InterruptedException e) {
				System.exit(1);
			}
			for(Long failure : failures) {
				System.out.println(failure);
			}
			System.out.println("Failures: " + failures.size());
			System.out.println("Number of pings: " + pings);
			System.out.println("Average ping time: " + totalPingTime / pings);
			System.exit(0);
		}
	}

	private static void startListener(final ConnectivityEngine engine) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					ServerSocket socket = new ServerSocket(6300);
					while(true) {
						new ReadThread(socket.accept(), callback).start();
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}).start();
	}

}
