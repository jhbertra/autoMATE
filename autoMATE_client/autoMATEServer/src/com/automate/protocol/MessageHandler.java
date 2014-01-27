package com.automate.protocol;

import java.io.OutputStream;

public abstract class MessageHandler<M extends Message<? extends ProtocolParameters>> {

	protected ProtocolManager manager;
	
	public MessageHandler(ProtocolManager manager) {
		this.manager = manager;
	}
	
	public abstract void handleMessage(M message, OutputStream out) throws MessageHandlingException;
	
}
