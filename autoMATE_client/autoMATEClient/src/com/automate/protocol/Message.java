package com.automate.protocol;

import java.util.Map;

import com.automate.protocol.client.ClientMessageSubParser;
import com.automate.util.xml.XmlConvertible;
import com.automate.util.xml.XmlFormatException;

public abstract class Message <P extends ProtocolParameters> extends XmlConvertible {

	private P parameters;
	
	public static final String AUTHENTICATION = "authentication";
	public static final String NODE_LIST = "node-list";
	public static final String COMMAND = "command";
	public static final String PING = "ping";
	public static final String STATUS_UPDATE = "status-update";
	public static final String WARNING = "warning";

	public static final int NUM_MESSAGE_TYPES = 6;
	
	public Message(P parameters) {
		this.parameters = parameters;
	}

	/* (non-Javadoc)
	 * @see com.automate.util.xml.XmlConvertible#constructXml(java.lang.StringBuilder, int)
	 */
	@Override
	protected void constructXml(StringBuilder builder, int indentationLevel)
			throws XmlFormatException {
		addElement("message", false);
		
		parameters.toXml(builder, indentationLevel);
		
		addElement("content", false);
		
		addContent();
		
		closeElement(); // content
		
		closeElement(); // message
	}
	
	protected abstract void addContent() throws XmlFormatException;
	
}
