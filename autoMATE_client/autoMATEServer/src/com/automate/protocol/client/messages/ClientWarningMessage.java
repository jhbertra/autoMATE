package com.automate.protocol.client.messages;

import com.automate.protocol.Message;
import com.automate.protocol.client.ClientProtocolParameters;
import com.automate.util.xml.Attribute;
import com.automate.util.xml.XmlFormatException;

public class ClientWarningMessage extends Message<ClientProtocolParameters> {

	private int warningId;
	
	public ClientWarningMessage(ClientProtocolParameters parameters, int warningId) {
		super(parameters);
		this.warningId = warningId;
	}

	@Override
	protected void addContent() throws XmlFormatException {
		addElement("warning", true, new Attribute("warning-id", String.valueOf(warningId)));
	}

	@Override
	public com.automate.protocol.Message.MessageType getMessageType() {
		return MessageType.WARNING;
	}

}
