package com.automate.server.connectivity;

import com.automate.server.connectivity.ConnectivityWatchdogThread.OnClientTimeoutListener;
import com.automate.server.connectivity.EngineCallback.ClientPingListener;

public class ConnectivityEngine implements OnClientTimeoutListener {

	private boolean terminated;
	private boolean running;
	private long interval;
	private int timeout;
	private ConnectivityWatchdogThread watchdogThread;
	private EngineCallback callback;
	private Thread executionThread;
	private final Object loopLock = new Object();

	/**
	 * Creates a new Connectivity engine.
	 * @param pingInterval
	 * @param callback
	 */
	public ConnectivityEngine(int pingIntervalSeconds, int timeout, EngineCallback callback) {
		if(timeout < 1) {
			throw new IllegalArgumentException("Minimum timeout is 1 second.");
		}
		if(pingIntervalSeconds < 5) {
			throw new IllegalArgumentException("Minimum Ping interval is 5 seconds.");
		}
		if(timeout >= pingIntervalSeconds) {
			throw new IllegalArgumentException("Timeout must be smaller than ping interval");
		}
		if(callback == null) {
			throw new NullPointerException("ConnectivityEngine callback was null.");
		}
		this.interval = pingIntervalSeconds;
		this.callback = callback;
		this.timeout = timeout;
		this.watchdogThread = new ConnectivityWatchdogThread(this);
	}
	
	/**
	 * Starts the connectivity engine on a background thread.
	 * @throws IllegalStateException if this method is called after the engine has been
	 * shutdown.
	 */
	public void start() throws IllegalStateException {
		if(terminated) {
			throw new IllegalStateException("Connectivity engine cannot be restarted after it has been shut down.");
		}
		if(running) {
			throw new IllegalStateException("Connectivity engine already running.");
		}
		synchronized (loopLock) {
			running = true;
		}
		executionThread = new Thread(new Runnable() {
			@Override
			public void run() {
				engineMainLoop();
			}
		}, "Connectivity Engine");
		executionThread.start();
	}

	void engineMainLoop() {
		synchronized (loopLock) { 					// lock the engine
			while(!terminated) {
				long startTime = System.currentTimeMillis();
				loopDelegate();
				long endTime = System.currentTimeMillis();
				long pingTime = endTime - startTime;
				try {
					long waitTime = Math.max(1, (interval * 1000) - pingTime);
					System.out.println("Connectivity engine sleeping for " + waitTime + " milliseconds.");
					loopLock.wait(waitTime);
				} catch (InterruptedException e) {} // release the engine lock and wait for the next ping interval.	
			}
		}
	}

	void loopDelegate() {
		callback.pingAllClients(new ClientPingListener() { // tell the callback to ping all the clients
			@Override
			public void clientAdded(ClientId id) {
				watchdogThread.setTimeout(id, timeout);
			}
		});
	}

	/**
	 * Tell the engine that an ack ping has been received.
	 * @param uid - the uid of the client that has acknowledged the ping
	 * @return true if the engine is waiting for an ack ping from this client.
	 */
	public boolean ackPingReceivedFromClient(ClientId uid) {
		return watchdogThread.cancelTimeout(uid);
	}

	/**
	 * Shuts down the engine. After this call, the engine cannot be restarted.
	 * @return true if the call had any effect.  false if the engine is already shut down.
	 */
	public boolean shutdown() {
		synchronized (loopLock) {
			if(!terminated) {
				terminated = true;
				running = false;
				loopLock.notify();
				this.callback = null;
				return true;
			}
		}
		return false;
	}

	/**
	 * @return the callback
	 */
	public EngineCallback getCallback() {
		return callback;
	}

	@Override
	public void onClientTimeout(ClientId client) {
		callback.connectionLost(client);
	}

}
