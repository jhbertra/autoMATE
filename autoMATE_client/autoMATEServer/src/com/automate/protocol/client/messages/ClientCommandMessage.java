package com.automate.protocol.client.messages;

import java.util.List;

import com.automate.protocol.Message;
import com.automate.protocol.client.ClientProtocolParameters;
import com.automate.protocol.models.CommandArgument;
import com.automate.util.xml.Attribute;
import com.automate.util.xml.XmlFormatException;

public class ClientCommandMessage extends Message<ClientProtocolParameters> {

	private String nodeId;
	private String name;
	private String commandId;
	
	private List<CommandArgument<?>> args;
	
	public ClientCommandMessage(ClientProtocolParameters parameters, String nodeId, String name, String commandId, List<CommandArgument<?>> args) {
		super(parameters);
		if(nodeId == null) {
			throw new NullPointerException("nodeId null in ClientCommandMessage.");
		}
		if(commandId == null) {
			throw new NullPointerException("commandId null in ClientCommandMessage.");
		}
		this.nodeId = nodeId;
		this.name = name;
		this.commandId = commandId;
		this.args = args;
	}

	@Override
	protected void addContent() throws XmlFormatException {
		if(args == null || args.isEmpty()) {
			if(name == null) {
				addElement("command", true
						, new Attribute("node-id", nodeId)
						, new Attribute("command-id", commandId));				
			} else {
				addElement("command", true
						, new Attribute("node-id", nodeId)
						, new Attribute("name", name)
						, new Attribute("command-id", commandId));
			}
		} else {
			if(name == null) {
				addElement("command", false
						, new Attribute("node-id", nodeId)
						, new Attribute("command-id", commandId));				
			} else {
				addElement("command", false
						, new Attribute("node-id", nodeId)
						, new Attribute("name", name)
						, new Attribute("command-id", commandId));
			}

			for(CommandArgument<?> arg : args) {
				arg.toXml(this.builder, this.indentationLevel);
			}
			
			closeElement();
		}
	}

	@Override
	public com.automate.protocol.Message.MessageType getMessageType() {
		return MessageType.COMMAND;
	}

}
