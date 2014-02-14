package com.automate.protocol.client;

import com.automate.protocol.Message;

public class ClientNodeListMessage extends Message <ClientProtocolParameters> {

	public ClientNodeListMessage(ClientProtocolParameters parameters) {
		super(parameters);
	}

	@Override
	protected void addContent() {
		addElement("node-list", true);
	}

}
