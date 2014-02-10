package com.automate.protocol.server;

import com.automate.protocol.Message;

public class ServerPingMessage extends Message<ServerProtocolParameters> {

	public ServerPingMessage(ServerProtocolParameters parameters) {
		super(parameters);
	}

	@Override
	protected void addContent() {
		addElement("ping", true);
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.PING;
	}
	
}
