package com.automate.protocol.server.subParsers;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.automate.protocol.server.messages.ServerAuthenticationMessage;

public class ServerAuthenticationMessageSubParser extends ServerMessageSubParser<ServerAuthenticationMessage> {
	
	private String username;
	private int responseCode;
	private String response;
	private String sessionKey;
	
	/* (non-Javadoc)
	 * @see com.automate.protocol.server.ServerMessageSubParser#endElement(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if(qName.equals("content")) {
			this.message = new ServerAuthenticationMessage(parameters, username, responseCode, response, sessionKey);
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
		if(qName.equals("authentication")) {
			username = attributes.getValue("username");
			String responseString = attributes.getValue("response");
			sessionKey = attributes.getValue("session-key");
			if(username == null || username.isEmpty()) {
				throw new SAXException("username was null.");
			} else if(sessionKey == null || sessionKey.isEmpty()) {
				throw new SAXException("session-key was null.");
			} else if(responseString == null || responseString.isEmpty()) {
				throw new SAXException("response was null.");
			}
			try {
				responseCode = Integer.parseInt(responseString.substring(0, 3));
			} catch(NumberFormatException e) {
				throw new SAXException("response code malformed, unable to parse code.");
			}
			response = responseString.substring(4);
		} else {
			super.startElement(uri, localName, qName, attributes);
		}
	}
}