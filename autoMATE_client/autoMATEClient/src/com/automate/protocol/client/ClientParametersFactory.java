package com.automate.protocol.client;

public class ClientParametersFactory {

	private static ClientParametersFactory instance = new ClientParametersFactory();
	
	private int majorVersion;
	private int minorVersion;
	private String sessionKey;
	
	public ClientProtocolParameters getProtocolParameters() {
		return new ClientProtocolParameters(majorVersion, minorVersion, sessionKey);
	}

	public static ClientParametersFactory getInstance() {
		return instance;
	}

	/**
	 * @param majorVersion the majorVersion to set
	 */
	public void setMajorVersion(int majorVersion) {
		this.majorVersion = majorVersion;
	}

	/**
	 * @param minorVersion the minorVersion to set
	 */
	public void setMinorVersion(int minorVersion) {
		this.minorVersion = minorVersion;
	}

	/**
	 * @param sessionKey the sessionKey to set
	 */
	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}
	
}
