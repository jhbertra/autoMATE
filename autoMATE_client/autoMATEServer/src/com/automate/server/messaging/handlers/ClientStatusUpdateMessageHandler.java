package com.automate.server.messaging.handlers;

import com.automate.protocol.Message;
import com.automate.protocol.client.messages.ClientStatusUpdateMessage;
import com.automate.protocol.server.ServerProtocolParameters;
import com.automate.server.messaging.IMessageHandler;

public class ClientStatusUpdateMessageHandler implements
		IMessageHandler<ClientStatusUpdateMessage, Void> {

	@Override
	public Message<ServerProtocolParameters> handleMessage(ServerProtocolParameters responseParameters,
			ClientStatusUpdateMessage message, Void params) {
		long nodeId = message.nodeId;
		
		return null;
	}

}
