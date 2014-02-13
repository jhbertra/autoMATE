package com.automate.protocol.client.subParsers;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.automate.protocol.client.messages.ClientStatusUpdateMessage;

public class ClientStatusUpdateSubParser extends ClientMessageSubParser<ClientStatusUpdateMessage> {

	private int nodeId;
	
	/* (non-Javadoc)
	 * @see com.automate.protocol.server.ServerMessageSubParser#endElement(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if(qName.equals("content")) {
			this.message = new ClientStatusUpdateMessage(parameters, nodeId);
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
		if(qName.equals("status-update")) {
			try {				
				nodeId = Integer.parseInt(attributes.getValue("node-id"));
			} catch(NumberFormatException e) {
				throw new SAXException(e);
			}
			if(nodeId < 0) {
				throw new SAXException("Illegal value for node id: " + nodeId);
			}
		} else {
			super.startElement(uri, localName, qName, attributes);
		}
	}
	
}
