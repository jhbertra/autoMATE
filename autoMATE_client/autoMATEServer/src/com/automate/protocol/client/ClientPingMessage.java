package com.automate.protocol.client;

import com.automate.protocol.Message;

public class ClientPingMessage extends Message<ClientProtocolParameters> {

	public ClientPingMessage(ClientProtocolParameters parameters) {
		super(parameters);
	}

	@Override
	protected void addContent() {
		addElement("ping", true);
	}
	
}
