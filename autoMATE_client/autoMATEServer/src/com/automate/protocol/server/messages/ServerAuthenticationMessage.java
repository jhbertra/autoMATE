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
		if(username == null) {
			throw new NullPointerException("username null in ServerAuthenticationMesssage.");
		}
		this.username = username;
		this.responseCode = responseCode;
		this.response = response;
		this.sessionKey = sessionKey;
	}

	@Override
	protected void addContent() {
		if(sessionKey != null) {
			addElement("authentication", true, 	new Attribute("username", username), 
												new Attribute("response", responseCode + (response != null ? (" " + response) : "")),
												new Attribute("session-key", sessionKey));
		} else {
			addElement("authentication", true, 	new Attribute("username", username), 
												new Attribute("response", responseCode + (response != null ? (" " + response) : "")));
		}
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.AUTHENTICATION;
	}
	
}
