package com.automate.protocol.client;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.automate.protocol.Message;
import com.automate.protocol.MessageFormatException;
import com.automate.protocol.MessageSubParser;
import com.automate.util.xml.XmlFormatException;

public abstract class ClientMessageSubParser <M extends Message<ClientProtocolParameters>> 
extends MessageSubParser<M, ClientProtocolParameters> {

	@Override
	protected ClientProtocolParameters parseParameters(XmlPullParser parser)
			throws XmlFormatException, XmlPullParserException, IOException,
			MessageFormatException {
		return null;
	}

}
