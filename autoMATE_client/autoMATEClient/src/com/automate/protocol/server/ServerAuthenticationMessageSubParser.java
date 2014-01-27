package com.automate.protocol.server;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.automate.protocol.MessageFormatException;
import com.automate.util.xml.XmlFormatException;

public class ServerAuthenticationMessageSubParser extends ServerMessageSubParser<ServerAuthenticationMessage> {

	@Override
	protected ServerAuthenticationMessage parseContent(XmlPullParser parser, ServerProtocolParameters parameters) 
			throws XmlFormatException, XmlPullParserException, IOException, MessageFormatException {
		String username = null, response = null, sessionKey = null;
		int responseCode = 0;
		int type = parser.nextTag();
		if(type == XmlPullParser.START_TAG) {
			if(parser.getName().equals("authentication")) {
				if(parser.getAttributeCount() == 3) {
					username = parser.getAttributeValue(null, "username");
					response = parser.getAttributeValue(null, "password");
					sessionKey = parser.getAttributeValue(null, "session-key");
					if(response == null) {
						throw new MessageFormatException("ServerAuthenticationMessage requires a response attribute.");
					} 
					response = response.trim();
					try {
						responseCode = Integer.parseInt(response.substring(0, 3));
					} catch (NumberFormatException e) {
						throw new MessageFormatException("Error parsing response code.", e);
					}
					response = response.substring(4);
				} else {
					throw new MessageFormatException("ServerAuthenticationMessage requires two attributes, encountered " +
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
			throw new MessageFormatException("ServerAuthenticationMessage requires a username attribute.");
		} else if(sessionKey == null) {
			throw new MessageFormatException("ServerAuthenticationMessage requires a session-key attribute.");
		} 
		return new ServerAuthenticationMessage(parameters, username, responseCode, response, sessionKey);
	}

}
