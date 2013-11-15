package com.automate.protocol;

import com.automate.protocol.client.ClientProtocolParameters;
import com.automate.util.xml.XmlConvertible;
import com.automate.util.xml.XmlFormatException;

public abstract class Message <P extends ProtocolParameters> extends XmlConvertible {

	private P parameters;
	
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
