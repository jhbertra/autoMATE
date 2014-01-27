package com.automate.protocol.client;

import com.automate.protocol.ProtocolParameters;

/**
 * Parameters for messages sent by the client.
 */
public class ClientProtocolParameters extends ProtocolParameters {

	public final String sessionKey;

	public ClientProtocolParameters(int majorVersion, int minorVersion, String sessionKey) {
		super(majorVersion, minorVersion);
		this.sessionKey = sessionKey;
	}

	private void addSessionKeyParameter() {
		addParameter("session-key", sessionKey);
	}

	@Override
	protected void addSpecializedParameters() {
		addSessionKeyParameter();
	}
	
}
