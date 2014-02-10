package com.automate.protocol.server.messages;

import com.automate.protocol.Message;
import com.automate.protocol.server.ServerProtocolParameters;
import com.automate.util.xml.Attribute;
import com.automate.util.xml.XmlFormatException;

public class ServerCommandMessage extends Message<ServerProtocolParameters> {

	private int commandId;
	private int responseCode;
	private String message;
	
	public ServerCommandMessage(ServerProtocolParameters parameters, int commandId, int responseCode, String message) {
		super(parameters);
		this.commandId = commandId;
		this.responseCode = responseCode;
		this.message = message;
	}

	@Override
	protected void addContent() throws XmlFormatException {
		if(message == null) {
			addElement("command", true
					, new Attribute("command-id", String.valueOf(commandId))
					, new Attribute("response-code", String.valueOf(responseCode)));
		} else {
			addElement("command", true
					, new Attribute("command-id", String.valueOf(commandId))
					, new Attribute("response-code", String.valueOf(responseCode))
					, new Attribute("message", message));
		}
	}

	@Override
	public com.automate.protocol.Message.MessageType getMessageType() {
		return MessageType.COMMAND;
	}

}
