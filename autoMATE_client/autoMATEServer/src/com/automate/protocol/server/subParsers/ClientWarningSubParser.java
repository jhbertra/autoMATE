package com.automate.protocol.server.subParsers;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.automate.protocol.server.messages.ServerWarningMessage;

public class ClientWarningSubParser extends ServerMessageSubParser<ServerWarningMessage> {

	private int warningId;
	private int nodeId;
	private String warningMessage;
	
	/* (non-Javadoc)
	 * @see com.automate.protocol.server.ServerMessageSubParser#endElement(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if(qName.equals("content")) {
			this.message = new ServerWarningMessage(parameters, warningId, nodeId, warningMessage);
		} else {
			super.endElement(uri, localName, qName);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.automate.protocol.server.ServerMessageSubParser#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if(qName.equals("warning")) {
			try {				
				warningId = Integer.parseInt(attributes.getValue("warning-id"));
				nodeId = Integer.parseInt(attributes.getValue("node-id:"));
			} catch(NumberFormatException e) {
				throw new SAXException(e);
			}
			if(warningId < 0) {
				throw new SAXException("Illegal value for warning id: " + warningId);
			}
			if(nodeId < 0) {
				throw new SAXException("Illegal value for node id: " + warningId);
			}
			warningMessage = attributes.getValue("message");
			if(warningMessage == null) {
				throw new SAXException("warning message was null.");
			}
		} else {
			super.startElement(uri, localName, qName, attributes);
		}
	}
	
}
