package com.automate.protocol.server.messages;

import com.automate.protocol.Message;
import com.automate.protocol.Message.MessageType;
import com.automate.protocol.server.ServerProtocolParameters;
import com.automate.util.xml.Attribute;

public class ServerAuthenticationMessage extends Message <ServerProtocolParameters> {

	public final String username;
	public final int responseCode;
	public final String response;
	public final String sessionKey;
	
	

	public ServerAuthenticationMessage(ServerProtocolParameters parameters,
			String username, int responseCode, String response, String sessionKey) {
		super(parameters);
		this.username = username;
		this.responseCode = responseCode;
		this.response = response;
		this.sessionKey = sessionKey;
	}

	@Override
	protected void addContent() {
		addElement("authentication", true, 	new Attribute("username", username), 
											new Attribute("response", responseCode + " " + response),
											new Attribute("session-key", sessionKey));
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.AUTHENTICATION;
	}
	
}
