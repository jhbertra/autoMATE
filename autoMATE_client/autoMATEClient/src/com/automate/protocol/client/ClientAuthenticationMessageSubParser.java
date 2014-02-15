package com.automate.protocol.client;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.automate.protocol.MessageFormatException;
import com.automate.util.xml.XmlFormatException;

public class ClientAuthenticationMessageSubParser extends ClientMessageSubParser<ClientAuthenticationMessage> {

	@Override
	protected ClientAuthenticationMessage parseContent(XmlPullParser parser, ClientProtocolParameters parameters) 
			throws XmlFormatException, XmlPullParserException, IOException, MessageFormatException {
		String username = null, password = null;
		int type = parser.nextTag();
		if(type == XmlPullParser.START_TAG) {
			if(parser.getName().equals("authentication")) {
				if(parser.getAttributeCount() == 2) {
					username = parser.getAttributeValue(null, "username");
					password = parser.getAttributeValue(null, "password");
				} else {
					throw new MessageFormatException("ClientAuthenticationMessage requires two attributes, encountered " +
							parser.getAttributeCount());
				}
			} else {
				throw new MessageFormatException("Unexpected element name: \"" + parser.getName() + "\", expected" +
						" \"authentication\".");
			}
		} else {
			throw new XmlFormatException("Unexpected event type " + type);
		}
		if(username == null) {
			throw new MessageFormatException("ClientAuthenticationMessage requires a username attribute.");
		} else if(password == null) {
			throw new MessageFormatException("ClientAuthenticationMessage requires a password attribute.");
		} 
		return new ClientAuthenticationMessage(parameters, username, password);
	}
}
