package com.automate.protocol.server;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.automate.protocol.Message;
import com.automate.protocol.MessageFormatException;
import com.automate.protocol.MessageSubParser;
import com.automate.util.xml.XmlFormatException;

public abstract class ServerMessageSubParser <M extends Message<ServerProtocolParameters>> 
extends MessageSubParser<M, ServerProtocolParameters> {

	@Override
	protected ServerProtocolParameters parseParameters(XmlPullParser parser)
			throws XmlFormatException, XmlPullParserException, IOException,
			MessageFormatException {
		return null;
	}

}
