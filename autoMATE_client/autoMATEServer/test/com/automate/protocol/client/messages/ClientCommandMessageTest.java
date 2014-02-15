package com.automate.protocol.client.messages;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.automate.protocol.client.ClientProtocolParameters;
import com.automate.protocol.models.CommandArgument;
import com.automate.protocol.models.Type;
import com.automate.util.xml.XmlFormatException;

public class ClientCommandMessageTest {

	private ClientCommandMessage subject;
	
	private ClientProtocolParameters parameters = new ClientProtocolParameters(0, 0, "");
	
	@Test(expected=NullPointerException.class)
	public void testNullConstructorArgs() {
		subject = new ClientCommandMessage(parameters, null, null, null, null);
	}
	
	@Test(expected=NullPointerException.class)
	public void testNullNodeId() {
		List<CommandArgument<?>> args = new ArrayList<CommandArgument<?>>();
		subject = new ClientCommandMessage(parameters, null, "command", "commandId", args);
	}
	
	@Test
	public void testNullCommandName() {
		List<CommandArgument<?>> args = new ArrayList<CommandArgument<?>>();
		subject = new ClientCommandMessage(parameters, "node", null, "commandId", args);
	}
	
	@Test(expected=NullPointerException.class)
	public void testNullCommandId() {
		List<CommandArgument<?>> args = new ArrayList<CommandArgument<?>>();
		subject = new ClientCommandMessage(parameters, "node", "command", null, args);
	}
	
	@Test
	public void testNullArgs() {
		subject = new ClientCommandMessage(parameters, "node", "command", "commandId", null);
	}
	
	@Test
	public void testToXmlNoNullsNoArgs() {
		List<CommandArgument<?>> args = new ArrayList<CommandArgument<?>>();
		subject = new ClientCommandMessage(parameters, "node", "command", "commandId", args);
		StringBuilder builder = new StringBuilder();
		try {
			subject.toXml(builder, 0);
		} catch (XmlFormatException e) {
			fail(e.getMessage());
		}
		String expected = 	"content-type:command\n" +
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
							"<message >\n" +
							"\t<parameters >\n" +
							"\t\t<parameter name=\"version\" value=\"0.0\" />\n" +
							"\t\t<parameter name=\"session-key\" value=\"\" />\n" +
							"\t</parameters>\n" +
							"\t<content >\n" +
							"\t\t<command node-id=\"node\" name=\"command\" command-id=\"commandId\" />\n" +
							"\t</content>\n" +
							"</message>\n";
		String actual = builder.toString();
		assertEquals(expected, actual);
	}
	
	@Test
	public void testToXmlNoNullsOneArg() {
		List<CommandArgument<?>> args = new ArrayList<CommandArgument<?>>();
		args.add(CommandArgument.newCommandArgument("arg", Type.INTEGER, 10));
		subject = new ClientCommandMessage(parameters, "node", "command", "commandId", args);
		StringBuilder builder = new StringBuilder();
		try {
			subject.toXml(builder, 0);
		} catch (XmlFormatException e) {
			fail(e.getMessage());
		}
		String expected = 	"content-type:command\n" +
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
							"<message >\n" +
							"\t<parameters >\n" +
							"\t\t<parameter name=\"version\" value=\"0.0\" />\n" +
							"\t\t<parameter name=\"session-key\" value=\"\" />\n" +
							"\t</parameters>\n" +
							"\t<content >\n" +
							"\t\t<command node-id=\"node\" name=\"command\" command-id=\"commandId\" >\n" +
							"\t\t\t<argument name=\"arg\" type=\"integer\" value=\"10\" />\n" +
							"\t\t</command>\n" +
							"\t</content>\n" +
							"</message>\n";
		String actual = builder.toString();
		assertEquals(expected, actual);
	}
	
	@Test
	public void testToXmlNoNullsTwoArgs() {
		List<CommandArgument<?>> args = new ArrayList<CommandArgument<?>>();
		args.add(CommandArgument.newCommandArgument("arg1", Type.INTEGER, 10));
		args.add(CommandArgument.newCommandArgument("arg2", Type.PERCENT, "50%"));
		subject = new ClientCommandMessage(parameters, "node", "command", "commandId", args);
		StringBuilder builder = new StringBuilder();
		try {
			subject.toXml(builder, 0);
		} catch (XmlFormatException e) {
			fail(e.getMessage());
		}
		String expected = 	"content-type:command\n" +
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
							"<message >\n" +
							"\t<parameters >\n" +
							"\t\t<parameter name=\"version\" value=\"0.0\" />\n" +
							"\t\t<parameter name=\"session-key\" value=\"\" />\n" +
							"\t</parameters>\n" +
							"\t<content >\n" +
							"\t\t<command node-id=\"node\" name=\"command\" command-id=\"commandId\" >\n" +
							"\t\t\t<argument name=\"arg1\" type=\"integer\" value=\"10\" />\n" +
							"\t\t\t<argument name=\"arg2\" type=\"percent\" value=\"50.0%\" />\n" +
							"\t\t</command>\n" +
							"\t</content>\n" +
							"</message>\n";
		String actual = builder.toString();
		assertEquals(expected, actual);
	}
	
	@Test
	public void testToXmlNullCommandName() {
		List<CommandArgument<?>> args = new ArrayList<CommandArgument<?>>();
		args.add(CommandArgument.newCommandArgument("arg1", Type.INTEGER, 10));
		args.add(CommandArgument.newCommandArgument("arg2", Type.PERCENT, "50%"));
		subject = new ClientCommandMessage(parameters, "node", null, "commandId", args);
		StringBuilder builder = new StringBuilder();
		try {
			subject.toXml(builder, 0);
		} catch (XmlFormatException e) {
			fail(e.getMessage());
		}
		String expected = 	"content-type:command\n" +
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
							"<message >\n" +
							"\t<parameters >\n" +
							"\t\t<parameter name=\"version\" value=\"0.0\" />\n" +
							"\t\t<parameter name=\"session-key\" value=\"\" />\n" +
							"\t</parameters>\n" +
							"\t<content >\n" +
							"\t\t<command node-id=\"node\" command-id=\"commandId\" >\n" +
							"\t\t\t<argument name=\"arg1\" type=\"integer\" value=\"10\" />\n" +
							"\t\t\t<argument name=\"arg2\" type=\"percent\" value=\"50.0%\" />\n" +
							"\t\t</command>\n" +
							"\t</content>\n" +
							"</message>\n";
		String actual = builder.toString();
		assertEquals(expected, actual);
	}
	
	@Test
	public void testToXmlNullArgs() {
		subject = new ClientCommandMessage(parameters, "node", "command", "commandId", null);
		StringBuilder builder = new StringBuilder();
		try {
			subject.toXml(builder, 0);
		} catch (XmlFormatException e) {
			fail(e.getMessage());
		}
		String expected = 	"content-type:command\n" +
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
							"<message >\n" +
							"\t<parameters >\n" +
							"\t\t<parameter name=\"version\" value=\"0.0\" />\n" +
							"\t\t<parameter name=\"session-key\" value=\"\" />\n" +
							"\t</parameters>\n" +
							"\t<content >\n" +
							"\t\t<command node-id=\"node\" name=\"command\" command-id=\"commandId\" />\n" +
							"\t</content>\n" +
							"</message>\n";
		String actual = builder.toString();
		assertEquals(expected, actual);
	}

}
