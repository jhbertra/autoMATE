package com.automate.protocol.server.subParsers;

import static org.junit.Assert.*;

import java.util.ArrayList;


import org.junit.Test;
import org.xml.sax.SAXException;

import com.automate.protocol.models.Status;
import com.automate.protocol.models.Type;
import com.automate.protocol.server.ServerProtocolParameters;
import com.automate.protocol.server.messages.ServerPingMessage;
import com.automate.protocol.server.messages.ServerStatusUpdateMessage;

public class ServerStatusUpdateMessageSubParserTest {

	private ServerStatusUpdateMessageSubParser subject;
	
	private String xml1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
			"<message >\n" +
			"\t<parameters >\n" +
			"\t\t<parameter name=\"version\" value=\"0.0\" />\n" +
			"\t\t<parameter name=\"session-valid\" value=\"true\" />\n" +
			"\t</parameters>\n" +
			"\t<content >\n" +
			"\t\t<status-update />\n" +
			"\t</content>\n" +
			"</message>\n";
	
	private String xml2 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
			"<message >\n" +
			"\t<parameters >\n" +
			"\t\t<parameter name=\"version\" value=\"0.0\" />\n" +
			"\t\t<parameter name=\"session-valid\" value=\"true\" />\n" +
			"\t</parameters>\n" +
			"\t<content >\n" +
			"\t\t<status-update node-id=\"0\" />\n" +
			"\t</content>\n" +
			"</message>\n";
	
	private String xml3 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
			"<message >\n" +
			"\t<parameters >\n" +
			"\t\t<parameter name=\"version\" value=\"0.0\" />\n" +
			"\t\t<parameter name=\"session-valid\" value=\"true\" />\n" +
			"\t</parameters>\n" +
			"\t<content >\n" +
			"\t\t<status-update node-id=\"0\" >\n" +
			"\t\t\t<status component-name=\"Temperature\" type=\"integer\" value=\"375\" />" +
			"\t\t</status-update>\n" +
			"\t</content>\n" +
			"</message>\n";
	
	private ServerProtocolParameters parameters = new ServerProtocolParameters(0, 0, true);
	
	@Test(expected=SAXException.class)
	public void testNoNodeId() throws Exception {
		subject = new ServerStatusUpdateMessageSubParser();
		subject.parseXml(xml1);
	}
	
	@Test
	public void testNoStatuses() throws Exception {
		subject = new ServerStatusUpdateMessageSubParser();
		ServerStatusUpdateMessage expected = new ServerStatusUpdateMessage(parameters, 0, null);
		assertEquals(expected, subject.parseXml(xml2));
	}
	
	@Test
	public void testOneStatus() throws Exception {
		subject = new ServerStatusUpdateMessageSubParser();
		ArrayList<Status<?>>statuses = new ArrayList<Status<?>>();
		statuses.add(Status.newStatus("Temperature", Type.INTEGER, 375));
		ServerStatusUpdateMessage expected = new ServerStatusUpdateMessage(parameters, 0, statuses);
		assertEquals(expected, subject.parseXml(xml3));
	}
	
}
