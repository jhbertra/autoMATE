package com.automate.protocol.client;

import com.automate.protocol.Message;
import com.automate.protocol.Message.MessageType;

public class ClientNodeListMessage extends Message <ClientProtocolParameters> {

	public ClientNodeListMessage(ClientProtocolParameters parameters) {
		super(parameters);
	}

	@Override
	protected void addContent() {
		addElement("node-list", true);
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.NODE_LIST;
	}

}
