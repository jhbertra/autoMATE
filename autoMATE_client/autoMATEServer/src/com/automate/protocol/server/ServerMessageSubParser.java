package com.automate.protocol.server;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.automate.protocol.Message;
import com.automate.protocol.MessageSubParser;

public abstract class ServerMessageSubParser <M extends Message<ServerProtocolParameters>> 
extends MessageSubParser<M, ServerProtocolParameters> {
	
	protected ServerProtocolParameters parameters;
	private int majorVersion, minorVersion;
	private boolean sessionValid;
	

	/* (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if(qName.equals("parameters")) {
			parameters = new ServerProtocolParameters(majorVersion, minorVersion, sessionValid);
		}
	}

	/* (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if(qName.equals("parameter")) {
			String name = attributes.getValue("name");
			String value = attributes.getValue("value");
			if(value == null || value.isEmpty()) {
				throw new SAXException("parameter tag had no value.");
			}
			if(name.equals("version")) {
				String [] versionParts = value.split("\\.");
				if(versionParts.length != 2) {
					throw new SAXException("Version parameter value malformed: " + value);
				}
				try {
					majorVersion = Integer.parseInt(versionParts[0]);
					minorVersion = Integer.parseInt(versionParts[1]);
				} catch(NumberFormatException e) {
					throw new SAXException("Version parameter value not numeric: " + value, e);
				}
			} else if(name.equals("session-valid")) {
				if(!value.equalsIgnoreCase("false") || !value.equalsIgnoreCase("true")) {
					throw new SAXException("session-valid expects a boolean value, got " + value);
				}
				sessionValid = Boolean.parseBoolean(value);
			} else {
				throw new SAXException("parameter " + name + " invalid.");
			}
		}
	}

}
