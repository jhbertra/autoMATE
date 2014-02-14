package com.automate.protocol.client;

import com.automate.protocol.ProtocolParameters;
import com.automate.util.xml.Attribute;
import com.automate.util.xml.XmlConvertible;
import com.automate.util.xml.XmlFormatException;

/**
 * Parameters for messages sent by the client.
 */
public class ClientProtocolParameters extends ProtocolParameters {

	private String sessionKey;

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
