package com.automate.server.connectivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReadThread extends Thread {

	private Socket socket;
	private Callback callback;
	
	public ReadThread(Socket accept, Callback callback) {
		super("Read Thread");
		this.socket = accept;
		this.callback = callback;
	}

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//			System.out.println("Reading input.");
			String line = reader.readLine();
//			System.out.println("Read input: " + line);
			if(line.equals("register")) {
				int id = callback.registerSocket(socket);
				System.out.println("Registering client " + id);
			} else if(line.startsWith("ping")) {
				String uid = line.substring(5);
				callback.onPingRecieved(new ClientId(ClientType.APP, uid));
			}
			socket.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static interface Callback {
		
		public int registerSocket(Socket socket);
		public void onPingRecieved(ClientId client);
		
	}
	
}
