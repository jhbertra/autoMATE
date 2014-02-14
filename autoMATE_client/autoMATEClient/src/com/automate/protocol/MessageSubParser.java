package com.automate.protocol;

import java.io.IOException;
import java.io.StringReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.automate.util.xml.XmlFormatException;

public abstract class MessageSubParser<M extends Message<?>, P extends ProtocolParameters> {
	
	public M parseXml(String xml) throws XmlFormatException, XmlPullParserException, IOException, MessageFormatException {
		XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
		parser.setInput(new StringReader(xml));
		P parameters = null;
		int type = parser.nextTag();
		if(type == XmlPullParser.START_TAG) {
			if(parser.getText().equals("message")) {
				type = parser.nextTag();
				if(type == XmlPullParser.START_TAG) {
					if(parser.getText().equals("parameters")) {
						parameters = parseParameters(parser);
						type = parser.nextTag();
						if(type != XmlPullParser.END_TAG) {
							throw new XmlFormatException("Close tag expected after parameters.");
						}
					} else {
						throw new MessageFormatException("first nested element was not parameters in " + getClass().getName() + ".parse(String xml)");
					}
				} else {
					throw new XmlFormatException("unexpected CLOSE_TAG event after opening message tag in " + getClass().getName() + ".parse(String xml)");
				}
				type = parser.nextTag();
				if(type == XmlPullParser.START_TAG) {
					if(parser.getText().equals("content")) {
						M message = parseContent(parser, parameters);
						type = parser.nextTag();
						if(type != XmlPullParser.END_TAG) {
							throw new XmlFormatException("Close tag expected after content.");
						}
						return message;
					} else {
						throw new MessageFormatException("second nested element was not content in " + getClass().getName() + ".parse(String xml)");
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
	
	protected abstract P parseParameters(XmlPullParser parser) throws XmlFormatException, XmlPullParserException, IOException, MessageFormatException;
	
	protected abstract M parseContent(XmlPullParser parser, P parameters) throws XmlFormatException, XmlPullParserException, IOException, MessageFormatException ;
	
}
