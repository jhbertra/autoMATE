package com.automate.protocol.server.messages;

import java.util.List;

import com.automate.protocol.Message;
import com.automate.protocol.Message.MessageType;
import com.automate.protocol.models.Node;
import com.automate.protocol.server.ServerProtocolParameters;
import com.automate.util.xml.Attribute;
import com.automate.util.xml.XmlFormatException;

public class ServerNodeListMessage extends Message <ServerProtocolParameters> {

	public final List<Node> nodes;

	public ServerNodeListMessage(ServerProtocolParameters parameters, List<Node> nodes) {
		super(parameters);
		this.nodes = nodes;
	}

	@Override
	protected void addContent() throws XmlFormatException {
		addElement("node-list", false);
		
		for(Node node : nodes) {
			node.toXml(this.builder, this.indentationLevel);
		}
		
		closeElement();
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.NODE_LIST;
	}

}
