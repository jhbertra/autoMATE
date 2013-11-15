package com.automate.protocol;

import java.io.IOException;
import java.io.StringReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.automate.protocol.server.ServerProtocolParameters;
import com.automate.util.xml.XmlFormatException;

public abstract class MessageSubParser<M extends Message<?>> {

	public M parseXml(String xml) throws XmlFormatException, XmlPullParserException, IOException {
		XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
		parser.setInput(new StringReader(xml));
		ServerProtocolParameters parameters = null;
		int type = parser.nextTag();
		if(type == XmlPullParser.START_TAG) {
			if(parser.getText().equals("message")) {
				type = parser.nextTag();
				if(type == XmlPullParser.START_TAG) {
					if(parser.getText().equals("parameters")) {
						parameters = parseParameters(parser);
					} else {
						throw new XmlFormatException("first nested element was not parameters in " + getClass().getName() + ".parse(String xml)");
					}
				} else {
					throw new XmlFormatException("unexpected CLOSE_TAG event after opening message tag in " + getClass().getName() + ".parse(String xml)");
				}
				type = parser.nextTag();
				if(type == XmlPullParser.START_TAG) {
					if(parser.getText().equals("content")) {
						return parseContent(parser, parameters);
					} else {
						throw new XmlFormatException("second nested element was not content in " + getClass().getName() + ".parse(String xml)");
					}
				} else {
					throw new XmlFormatException("unexpected CLOSE_TAG event after parameters in " + getClass().getName() + ".parse(String xml)");
				}
			} else {
				throw new XmlFormatException("xml root element was not message in " + getClass().getName() + ".parse(String xml)");
			}
		} else {
			throw new XmlFormatException("xml did not start with open tag in " + getClass().getName() + ".parse(String xml)");
		}
	}
	
	private ServerProtocolParameters parseParameters(XmlPullParser parser) {
		
	}
	
	protected abstract M parseContent(XmlPullParser parser, ServerProtocolParameters parameters);
	
}
