package com.automate.protocol.client;

import com.automate.protocol.Message;
import com.automate.protocol.Message.MessageType;

public class ClientPingMessage extends Message<ClientProtocolParameters> {

	public ClientPingMessage(ClientProtocolParameters parameters) {
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
