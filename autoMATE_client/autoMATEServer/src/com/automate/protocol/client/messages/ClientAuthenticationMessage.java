package com.automate.protocol.client.messages;

import com.automate.protocol.Message;
import com.automate.protocol.Message.MessageType;
import com.automate.protocol.client.ClientProtocolParameters;
import com.automate.util.xml.Attribute;

public class ClientAuthenticationMessage extends Message <ClientProtocolParameters> {

	public final String username;
	public final String password;
	
	public ClientAuthenticationMessage(ClientProtocolParameters parameters, String username, String password) {
		super(parameters);
		if(username == null) {
			throw new NullPointerException("username was null in ClientAuthenticationMessage");
		}
		if(password == null) {
			throw new NullPointerException("password was null in ClientAuthenticationMessage");
		}
		this.username = username;
		this.password = password;
	}

	@Override
	protected void addContent() {
		addElement("authentication", true, new Attribute("username", username), new Attribute("password", password));
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.AUTHENTICATION;
	}

}
