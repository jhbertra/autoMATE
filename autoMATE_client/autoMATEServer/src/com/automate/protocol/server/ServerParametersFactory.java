package com.automate.protocol.server;

public class ServerParametersFactory {

	private static ServerParametersFactory instance = new ServerParametersFactory();
	
	private int majorVersion;
	private int minorVersion;
	
	public ServerProtocolParameters getProtocolParameters(boolean sessionValid) {
		return new ServerProtocolParameters(majorVersion, minorVersion, sessionValid);
	}

	public static ServerParametersFactory getInstance() {
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
	
}
