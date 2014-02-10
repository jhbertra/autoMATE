package com.automate.protocol.server.messages;

import static org.junit.Assert.*;

import org.junit.Test;

import com.automate.protocol.server.ServerProtocolParameters;
import com.automate.util.xml.XmlFormatException;

public class ServerCommandMessageTest {

	private ServerCommandMessage subject;
	
	private ServerProtocolParameters parameters = new ServerProtocolParameters(0, 0, true);
	
	@Test(expected=NullPointerException.class)
	public void testNullArgs() {
		subject = new ServerCommandMessage(parameters, null, 0, null);
	}
	
	@Test
	public void testNullMssage() {
		subject = new ServerCommandMessage(parameters, "command", 0, null);
	}
	
	@Test
	public void testToXmlNoNulls() {
		subject = new ServerCommandMessage(parameters, "command", 200, "message");
		StringBuilder builder = new StringBuilder();
		try {
			subject.toXml(builder, 0);
		} catch (XmlFormatException e) {
			fail(e.getMessage());
		}
		String expected = 	"content-type:command\n" +
							"<message >\n" +
							"\t<parameters >\n" +
							"\t\t<parameter name=\"version\" value=\"0.0\" />\n" +
							"\t\t<parameter name=\"session-valid\" value=\"true\" />\n" +
							"\t</parameters>\n" +
							"\t<content >\n" +
							"\t\t<command command-id=\"command\" response-code=\"200\" message=\"message\" />\n" +
							"\t</content>\n" +
							"</message>\n";
		String actual = builder.toString();
		assertEquals(expected, actual);
	}
	
	@Test
	public void testToXmlNullMessage() {
		subject = new ServerCommandMessage(parameters, "command", 200, null);
		StringBuilder builder = new StringBuilder();
		try {
			subject.toXml(builder, 0);
		} catch (XmlFormatException e) {
			fail(e.getMessage());
		}
		String expected = 	"content-type:command\n" +
							"<message >\n" +
							"\t<parameters >\n" +
							"\t\t<parameter name=\"version\" value=\"0.0\" />\n" +
							"\t\t<parameter name=\"session-valid\" value=\"true\" />\n" +
							"\t</parameters>\n" +
							"\t<content >\n" +
							"\t\t<command command-id=\"command\" response-code=\"200\" />\n" +
							"\t</content>\n" +
							"</message>\n";
		String actual = builder.toString();
		assertEquals(expected, actual);
	}

}
