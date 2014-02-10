package com.automate.protocol.server.messages;

import java.util.List;

import com.automate.protocol.Message;
import com.automate.protocol.models.Status;
import com.automate.protocol.server.ServerProtocolParameters;
import com.automate.util.xml.Attribute;
import com.automate.util.xml.XmlFormatException;

public class ServerStatusUpdateMessage extends Message<ServerProtocolParameters> {
	
	private int nodeId;
	
	private List<Status<?>> statuses;
	
	public ServerStatusUpdateMessage(ServerProtocolParameters parameters, int nodeId) {
		super(parameters);
		this.nodeId = nodeId;
	}

	@Override
	protected void addContent() throws XmlFormatException {
		addElement("status-update", false, new Attribute("node-id", String.valueOf(nodeId)));
		
		for(Status<?> status : statuses) {
			status.toXml(this.builder, this.indentationLevel);
		}
		
		closeElement();
	}

	@Override
	public com.automate.protocol.Message.MessageType getMessageType() {
		return MessageType.STATUS_UPDATE;
	}

}
