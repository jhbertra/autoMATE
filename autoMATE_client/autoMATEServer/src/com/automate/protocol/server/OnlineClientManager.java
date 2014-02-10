package com.automate.protocol.server;

import java.util.List;

import com.automate.server.connectivity.ClientId;

public interface OnlineClientManager {

	public void removeClient(ClientId clientId);
	public List<ClientId> getClientsQueuedForPings();
	public void clientPinged(ClientId id);
	public void reconcilePingedClient(ClientId id);
	
}
