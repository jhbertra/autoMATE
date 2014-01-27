package com.automate.protocol.server;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.automate.protocol.MessageFormatException;
import com.automate.util.xml.XmlFormatException;

public class ServerPingMessageSubParser extends ServerMessageSubParser<ServerPingMessage> {

	@Override
	protected ServerPingMessage parseContent(XmlPullParser parser, ServerProtocolParameters parameters) 
					throws XmlFormatException, XmlPullParserException, IOException, MessageFormatException {
		int type = parser.nextTag();
		if(type == XmlPullParser.START_TAG) {
			if(!parser.getName().equals("ping")) {
				throw new MessageFormatException("Unexpected element name: \"" + parser.getName() + "\", expected" +
						" \"ping\".");
			}
		} else {
			throw new XmlFormatException("Unexpected event type " + type);
		}
		return new ServerPingMessage(parameters);
	}

}
