package com.automate.protocol.client;

import com.automate.protocol.Message;
import com.automate.util.xml.Attribute;

public class ClientAuthenticationMessage extends Message <ClientProtocolParameters> {

	public final String username;
	public final String password;
	
	public ClientAuthenticationMessage(ClientProtocolParameters parameters, String username, String password) {
		super(parameters);
		this.username = username;
		this.password = password;
	}

	@Override
	protected void addContent() {
		addElement("authentication", true, new Attribute("username", username), new Attribute("password", password));
	}

}
