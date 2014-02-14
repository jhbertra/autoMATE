package com.automate.protocol.server.messages;

import com.automate.protocol.Message;
import com.automate.protocol.server.ServerProtocolParameters;
import com.automate.util.xml.Attribute;
import com.automate.util.xml.XmlFormatException;

public class ServerCommandMessage extends Message<ServerProtocolParameters> {

	private String commandId;
	private int responseCode;
	private String message;
	
	public ServerCommandMessage(ServerProtocolParameters parameters, String commandId, int responseCode, String message) {
		super(parameters);
		if(commandId == null) {
			throw new NullPointerException("command-id was null in ServerCommandMessage.");
		}
		this.commandId = commandId;
		this.responseCode = responseCode;
		this.message = message;
	}

	@Override
	protected void addContent() throws XmlFormatException {
		if(message == null) {
			addElement("command", true
					, new Attribute("command-id", commandId)
					, new Attribute("response-code", String.valueOf(responseCode)));
		} else {
			addElement("command", true
					, new Attribute("command-id", commandId)
					, new Attribute("response-code", String.valueOf(responseCode))
					, new Attribute("message", message));
		}
	}

	@Override
	public com.automate.protocol.Message.MessageType getMessageType() {
		return MessageType.COMMAND;
	}

	/* (non-Javadoc)
	 * @see com.automate.protocol.Message#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(super.equals(obj)) {
			return	this.commandId.equals(((ServerCommandMessage)obj).commandId)
					&& this.responseCode == ((ServerCommandMessage)obj).responseCode
					&& (this.message == null ? 
						((ServerCommandMessage)obj).message == null 
						: this.message.equals(((ServerCommandMessage)obj).message));
		} else return false;
	}

}
