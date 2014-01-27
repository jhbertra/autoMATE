package com.automate.protocol.server;

import java.util.List;

import com.automate.server.connectivity.ClientId;

public class ClientPingThread extends Thread {

	private List<ClientId> onlineClients;
	private int batchNumber;
	private int batchSize;
	private ClientPingCallback callback;
	
	public ClientPingThread(List<ClientId> onlineClients, int batchNumber, int batchSize, ClientPingCallback callback) {
		this.onlineClients = onlineClients;
		this.batchNumber = batchNumber;
		this.batchSize = batchSize;
		this.callback = callback;
	}

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		int start = batchNumber * batchSize;
		int end = start + batchSize;
		for(int i = start; i < end && i < onlineClients.size(); ++i) {
			callback.pingClient(onlineClients.get(i));
		}
	}
	
	interface ClientPingCallback {
		void pingClient(ClientId id);
	}

}
