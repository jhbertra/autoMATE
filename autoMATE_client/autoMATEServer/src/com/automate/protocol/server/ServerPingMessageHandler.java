package com.automate.protocol.server;

import java.io.OutputStream;
import java.util.List;

import com.automate.protocol.MessageHandler;
import com.automate.protocol.MessageHandlingException;
import com.automate.protocol.ProtocolManager;
import com.automate.protocol.client.ClientPingMessage;
import com.automate.protocol.server.ClientPingThread.ClientPingCallback;
import com.automate.server.connectivity.ClientId;
import com.automate.server.connectivity.ClientType;
import com.automate.server.connectivity.ConnectivityEngine;
import com.automate.server.connectivity.ConnectivityWatchdogThread;
import com.automate.server.connectivity.ConnectivityWatchdogThread.OnClientTimeoutListener;
import com.automate.server.connectivity.EngineCallback;

public class ServerPingMessageHandler extends MessageHandler<ClientPingMessage> implements EngineCallback, OnClientTimeoutListener {

	private ConnectivityEngine engine;
	private OnlineClientManager clientManager;
	
	private ConnectivityWatchdogThread watchdogThread;
	private int pingBatchSize;
	private int pingTimeout;
	
	public ServerPingMessageHandler(ProtocolManager manager, ConnectivityEngine engine, OnlineClientManager clientManager,
			int pingBatchSize, int pingTimeout) {
		super(manager);
		this.engine = engine;
		this.clientManager = clientManager;
		this.pingBatchSize = pingBatchSize;
		this.watchdogThread = new ConnectivityWatchdogThread(this);
		this.watchdogThread.start();
		this.pingTimeout = pingTimeout;
	}

	@Override
	public void handleMessage(ClientPingMessage message, OutputStream out) throws MessageHandlingException {
		engine.ackPingReceivedFromClient(new ClientId(ClientType.APP, message.getParameters().sessionKey));
	}

	@Override
	public void connectionLost(ClientId clientId) {
		clientManager.removeClient(clientId);
	}

	@Override
	public void pingAllClients(ClientPingListener listener) {
		List<ClientId> onlineClients = clientManager.getClientsQueuedForPings();
		ClientPingCallback callback = new ClientPingCallback() {
			@Override
			public void pingClient(ClientId id) {
				manager.sendMessage(new ServerPingMessage(ServerParametersFactory.getInstance().getProtocolParameters(true)), id.toString());
				watchdogThread.setTimeout(id, pingTimeout);
				clientManager.clientPinged(id);
			}
		};
		for(int i = 0; i * pingBatchSize < onlineClients.size(); ++i) {
			new ClientPingThread(onlineClients, i, pingBatchSize, callback).start();
		}
	}

	@Override
	public void onClientTimeout(ClientId client) {
		this.engine.onClientTimeout(client);
	}

}
