package com.automate.protocol.server;

import com.automate.protocol.ProtocolParameters;

/**
 * Parameters for messages sent by the server.
 */
public class ServerProtocolParameters extends ProtocolParameters {

	private boolean sessionValid;

	public ServerProtocolParameters(int majorVersion, int minorVersion, boolean sessionValid) {
		super(majorVersion, minorVersion);
		this.sessionValid = sessionValid;
	}

	private void addSessionValidParameter() {
		addParameter("session-valid", String.valueOf(sessionValid));
	}

	@Override
	protected void addSpecializedParameters() {
		addSessionValidParameter();
	}

	/* (non-Javadoc)
	 * @see com.automate.protocol.ProtocolParameters#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(super.equals(obj)) {
			return this.sessionValid == ((ServerProtocolParameters)obj).sessionValid;
		} else return false;
	}
	
}
