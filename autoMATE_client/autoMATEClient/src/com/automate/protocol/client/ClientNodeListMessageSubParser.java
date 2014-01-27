package com.automate.protocol.client;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.automate.protocol.MessageFormatException;
import com.automate.util.xml.XmlFormatException;

public class ClientNodeListMessageSubParser extends ClientMessageSubParser<ClientNodeListMessage> {

	@Override
	protected ClientNodeListMessage parseContent(XmlPullParser parser, ClientProtocolParameters parameters) 
			throws XmlFormatException, XmlPullParserException, IOException, MessageFormatException {
		int type = parser.nextTag();
		if(type == XmlPullParser.START_TAG) {
			if(!parser.getName().equals("node-list")) {
				throw new MessageFormatException("Unexpected element name: \"" + parser.getName() + "\", expected" +
						" \"node-list\".");
			}
		} else {
			throw new XmlFormatException("Unexpected event type " + type);
		}
		return new ClientNodeListMessage(parameters);
	}

}
