package com.automate.client.io;

import com.automate.io.PacketListener;
import com.automate.io.PacketReadThread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;


public class ClientPacketReadThread extends PacketReadThread {

	private Socket socket;

	public ClientPacketReadThread(PacketListener callback, Socket socket) {
		super(callback);
		this.socket = socket;
	}

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		try {
			OutputStream out = socket.getOutputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String packet = null;
			while(!cancelled) {
				if(packet != null) {
					callback.packetRecieved(packet, out);
				}
				StringBuilder builder = new StringBuilder();
				String line = null;
				while(!(line = in.readLine().trim()).isEmpty()) {
					builder.append(line).append('\n');
				}
				packet = builder.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
