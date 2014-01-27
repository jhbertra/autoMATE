package com.automate.client.connectivity;

import java.io.OutputStream;

public class ConnectivityEngine {

	private EngineCallback callback;

	public ConnectivityEngine(EngineCallback callback) {
		if(callback == null) {
			throw new NullPointerException("ConnectivityEngine callback was null in constructor.");
		}
		this.callback = callback;
	}

	public void ping(OutputStream out) {
		callback.ping(out);		
	}

}
