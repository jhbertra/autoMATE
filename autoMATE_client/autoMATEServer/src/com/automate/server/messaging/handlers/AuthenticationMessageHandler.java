package com.automate.server.messaging.handlers;

import com.automate.protocol.Message;
import com.automate.protocol.client.ClientAuthenticationMessage;
import com.automate.protocol.server.ServerAuthenticationMessage;
import com.automate.protocol.server.ServerProtocolParameters;
import com.automate.server.messaging.IMessageHandler;
import com.automate.server.security.ISecurityManager;

public class AuthenticationMessageHandler implements IMessageHandler<ClientAuthenticationMessage, AuthenticationMessageHandlerParams> {

	ISecurityManager securityManager;
	
	@Override
	public Message<ServerProtocolParameters> handleMessage(ServerProtocolParameters responseParameters, 
			ClientAuthenticationMessage message, AuthenticationMessageHandlerParams params) {
		try {
			String sessionKey = securityManager.authenticateClient(message.username, 
					message.password, message.getParameters().sessionKey, params.clientIpAddress);
			if(sessionKey != null) {
				return new ServerAuthenticationMessage(responseParameters, message.username, 200, "OK", sessionKey);
			} else {
				return new ServerAuthenticationMessage(responseParameters, message.username, 400, "DENIED", "");
			}
		} catch(Throwable t) {
			return new ServerAuthenticationMessage(responseParameters, message.username, 500, "INTERNAL SERVER ERROR", "");
		}
	}
	
}
