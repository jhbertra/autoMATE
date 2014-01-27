package com.automate.io;

import java.io.OutputStream;
import java.util.ArrayList;

import com.automate.util.LoggingTags;

import android.util.Log;

public abstract class PacketReceiver<T extends PacketReadThread> extends Thread implements PacketListener {
	
	private boolean terminated;
	private final Object cancelNotification = new Object();
	private ArrayList<PacketListener>listeners;
	private T packetReadThread;
	
	public PacketReceiver() {
		super("Packet Receiver");
		listeners = new ArrayList<PacketListener>();
	}

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		synchronized(cancelNotification) {
			startReadThread();
			while(!terminated) {
				try {
					cancelNotification.wait();
				} catch (InterruptedException e) {}
			}
		}
	}

	private void startReadThread() {
		try {
			packetReadThread = createReaderThread();
			packetReadThread.start();
			Log.v(LoggingTags.PACKET_IO, "starting PacketReadThread.");
		} catch (Exception e) {
			Log.e(LoggingTags.PACKET_IO, "Failed to start packet read thread.", e);
		}
	}
	
	protected abstract T createReaderThread();

	public boolean terminate() {
		if(terminated) {
			Log.w(LoggingTags.PACKET_IO, "Packet receiver caught attempt to shut down when already terminated.");
			return false;
		}
		synchronized (cancelNotification) {			
			terminated = true;
			cancelNotification.notify();
			packetReadThread.cancel();
			Log.v(LoggingTags.PACKET_IO, "Shutting down PacketReceiver.");
			return true;
		}
	}

	@Override
	public void packetRecieved(String xml, OutputStream responseStream) {
		for(PacketListener listener: listeners) {
			listener.packetRecieved(xml, responseStream);
		}
	}
	
	public void addListener(PacketListener listener) {
		if(listener == null) throw new NullPointerException("Cannot add null PacketListener.");
		if(!listeners.contains(listener)) {
			listeners.add(listener);
		} else {
			Log.w(LoggingTags.PACKET_IO, "Packet receiver caught an attempt to add a listener that was already listeneing.");
		}
	}
	
}
