package com.automate.protocol.server;

import java.util.HashMap;
import java.util.NoSuchElementException;

import com.automate.protocol.IncomingMessageParser;
import com.automate.protocol.Message;
import com.automate.protocol.MessageSubParser;

public class ServerIncomingMessageParser extends IncomingMessageParser<ServerProtocolParameters> {

	private HashMap<String, ServerMessageSubParser<?>> subParsers;
	
	public ServerIncomingMessageParser() {
		subParsers = new HashMap<String, ServerMessageSubParser<?>>(Message.NUM_MESSAGE_TYPES);
		subParsers.put(Message.AUTHENTICATION, new ServerAuthenticationMessageSubParser());
		subParsers.put(Message.NODE_LIST, new ServerNodeListMessageSubParser());
//		subParsers.put(Message.COMMAND, new ServerCommandMessageSubParser());
		subParsers.put(Message.PING, new ServerPingMessageSubParser());
//		subParsers.put(Message.STATUS_UPDATE, new ServerStatusUpdateMessageSubParser());
//		subParsers.put(Message.WARNING, new ServerWarningMessageSubParser());
	}


	@Override
	protected MessageSubParser<Message<ServerProtocolParameters>, ServerProtocolParameters> getSubParser(
			String contentType) throws NoSuchElementException {
		ServerMessageSubParser<?> parser = subParsers.get(contentType);
		if(parser == null) {
			throw new NoSuchElementException();
		}
		return (MessageSubParser<Message<ServerProtocolParameters>, ServerProtocolParameters>) parser;
	}

}
