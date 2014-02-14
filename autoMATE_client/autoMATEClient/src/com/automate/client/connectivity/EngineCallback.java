package com.automate.client.connectivity;

import java.io.OutputStream;

public interface EngineCallback {

	void ping(OutputStream out);
	
}
