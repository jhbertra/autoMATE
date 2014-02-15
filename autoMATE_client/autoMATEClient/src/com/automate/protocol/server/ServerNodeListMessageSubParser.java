package com.automate.protocol.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.automate.models.Node;
import com.automate.protocol.MessageFormatException;
import com.automate.util.xml.XmlFormatException;

public class ServerNodeListMessageSubParser extends ServerMessageSubParser<ServerNodeListMessage> {

	@Override
	protected ServerNodeListMessage parseContent(XmlPullParser parser, ServerProtocolParameters parameters) 
			throws XmlFormatException, XmlPullParserException, IOException, MessageFormatException {
		List<Node> nodes = new ArrayList<Node>();
		int type = parser.nextTag();
		if(type == XmlPullParser.START_TAG) {
			if(parser.getName().equals("node-list")) {
				Node node = parseNode(parser);
				while(node != null) {
					nodes.add(node);
					node = parseNode(parser);
				}
			} else {
				throw new MessageFormatException("Unexpected element name: \"" + parser.getName() + "\", expected" +
						" \"node-list\".");
			}
		} else {
			throw new XmlFormatException("Unexpected event type " + type);
		}
		return new ServerNodeListMessage(parameters, nodes);
	}

	private Node parseNode(XmlPullParser parser) throws XmlPullParserException, IOException, MessageFormatException {
		Node node = null;
		if(parser.nextTag() != XmlPullParser.START_TAG) {
			if(parser.getName().equals("node")) {
				String name = parser.getAttributeValue(null, "name");
				String idString = parser.getAttributeValue(null, "id");
				if(idString == null ) {
					throw new MessageFormatException("node id was not provided.");
				}
				int id = Integer.parseInt(idString);
				String manufacturerCode = parser.getAttributeValue(null, "manufacturer");
				String model = parser.getAttributeValue(null, "model");
				String maxVersion = parser.getAttributeValue(null, "maxVersion");
				String infoUrl = parser.getAttributeValue(null, "info-url");
				String commandListUrl = parser.getAttributeValue(null, "command-list-url");
				if(name == null ) {
					throw new MessageFormatException("node name was not provided.");
				} else if(manufacturerCode == null ) {
					throw new MessageFormatException("node manufacturer was not provided.");
				} else if(model == null ) {
					throw new MessageFormatException("node model was not provided.");
				} else if(maxVersion == null ) {
					throw new MessageFormatException("node maxVersion was not provided.");
				} else if(infoUrl == null ) {
					throw new MessageFormatException("node info-url was not provided.");
				} else if(commandListUrl == null ) {
					throw new MessageFormatException("node command-list-url was not provided.");
				}
				node = new Node(name, id, manufacturerCode, model, maxVersion, infoUrl, commandListUrl);
			} else {
				throw new MessageFormatException("ServerNodeListMessage only accepts nodes as nested elements.");
			}
		}
		return node;
	}

}
