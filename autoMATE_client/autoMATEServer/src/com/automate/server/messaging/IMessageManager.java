package com.automate.server.messaging;

import java.net.Socket;

import com.automate.protocol.Message;
import com.automate.protocol.server.ServerProtocolParameters;
import com.automate.server.IManager;

/**
 * Manager that handles the passing of protocol messages between clients, nodes, and the server.
 * @author jamie.bertram
 *
 */
public interface IMessageManager extends IManager {

	/**
	 * Send message to the client id'd by clientd.
	 * @param message
	 * @param sessionKey
	 */
	public void sendMessage(Message<ServerProtocolParameters> message, String sessionKey);

	/**
	 * Send message to the client id'd by clientd.  Calls the listener when the message was sent
	 * @param message  the message to be sent
	 * @param sessionKey the session key to send it to
	 * @param listener the listener to be notified when the message is sent
	 */
	public void sendMessage(Message<ServerProtocolParameters> message, String sessionKey, MessageSentListener listener);
	
	/**
	 * Handles input from a socket (called from a worker thread).
	 * @param socket the socket that is receiving data from a client.
	 */
	public void handleInput(Socket socket);
	
	public interface MessageSentListener {
		
		public void messageSent();
		
	}
	
}
