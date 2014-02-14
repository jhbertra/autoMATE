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
	
	public ServerStatusUpdateMessage(ServerProtocolParameters parameters, int nodeId, List<Status<?>> statuses) {
		super(parameters);
		this.nodeId = nodeId;
		this.statuses = statuses;
	}

	@Override
	protected void addContent() throws XmlFormatException {
		if(statuses == null || statuses.size() == 0) {
			addElement("status-update", true, new Attribute("node-id", String.valueOf(nodeId)));
		} else {
			addElement("status-update", false, new Attribute("node-id", String.valueOf(nodeId)));
			
			for(Status<?> status : statuses) {
				status.toXml(this.builder, this.indentationLevel);
			}
			
			closeElement();			
		}
	}

	@Override
	public com.automate.protocol.Message.MessageType getMessageType() {
		return MessageType.STATUS_UPDATE;
	}

	/* (non-Javadoc)
	 * @see com.automate.protocol.Message#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(super.equals(obj)) {
			return	this.nodeId == ((ServerStatusUpdateMessage)obj).nodeId
					&& (this.statuses == null ? 
						(((ServerStatusUpdateMessage)obj).statuses == null || ((ServerStatusUpdateMessage)obj).statuses.isEmpty()) 
						: this.statuses.equals(((ServerStatusUpdateMessage)obj).statuses));
		} else return false;
	}
}
