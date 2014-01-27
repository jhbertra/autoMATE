package com.automate.protocol.client;

import java.io.OutputStream;

import com.automate.client.connectivity.ConnectivityEngine;
import com.automate.client.connectivity.EngineCallback;
import com.automate.protocol.MessageHandler;
import com.automate.protocol.MessageHandlingException;
import com.automate.protocol.ProtocolManager;
import com.automate.protocol.server.ServerPingMessage;

public class ClientPingMessageHandler extends MessageHandler<ServerPingMessage> implements EngineCallback {

	private ConnectivityEngine engine;
	
	public ClientPingMessageHandler(ProtocolManager manager, ConnectivityEngine engine) {
		super(manager);
		this.engine = engine;
	}

	@Override
	public void handleMessage(ServerPingMessage message, OutputStream out) throws MessageHandlingException {
		engine.ping(out);
	}

	@Override
	public void ping(OutputStream out) {
		manager.sendMessage(new ClientPingMessage((ClientProtocolParameters) ClientParametersFactory
				.getInstance().getProtocolParameters()), out);
	}

}
