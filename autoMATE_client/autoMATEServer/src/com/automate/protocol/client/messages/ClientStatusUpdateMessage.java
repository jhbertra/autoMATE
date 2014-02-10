package com.automate.protocol.client.messages;

import com.automate.protocol.Message;
import com.automate.protocol.client.ClientProtocolParameters;
import com.automate.util.xml.Attribute;
import com.automate.util.xml.XmlFormatException;

public class ClientStatusUpdateMessage extends Message<ClientProtocolParameters> {

	private int nodeId;
	
	public ClientStatusUpdateMessage(ClientProtocolParameters parameters, int nodeId) {
		super(parameters);
		this.nodeId = nodeId;
	}

	@Override
	protected void addContent() throws XmlFormatException {
		addElement("status-update", true, new Attribute("node-id", String.valueOf(nodeId)));
	}

	@Override
	public com.automate.protocol.Message.MessageType getMessageType() {
		return MessageType.STATUS_UPDATE;
	}

}
