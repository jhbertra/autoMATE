package com.automate.server.connectivity;

/**
 * Interface implemented by aany classes that observe the ConnectivityEngine.
 * @author jamie.bertram
 *
 */
public interface EngineCallback {

	/**
	 * Tells the callback that connection has been lost with a list of clients.
	 * @param clientIds - the uids of the clients.
	 */
	public void connectionLost(ClientId clientIds);
	
	/**
	 * Tells the callback to ping all the clients
	 * @return a map of all clients that were pinged.
	 */
	public void pingAllClients(ClientPingListener listener);
	
	public static interface ClientPingListener {
		
		public void clientAdded(ClientId id);
		
	}
	
}
