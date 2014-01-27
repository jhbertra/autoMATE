package com.automate.server.connectivity;

import java.util.ArrayList;
import java.util.HashMap;

public class ConnectivityWatchdogThread extends Thread {

	private HashMap<Long, ArrayList<ClientId>> responseTimeoutToClientId = new HashMap<Long, ArrayList<ClientId>>();
	private HashMap<ClientId, Long> clientIdToTimeout = new HashMap<ClientId, Long>();

	private final Object lock = new Object();

	private long currentSecond;
	private long startTime;

	private boolean cancelled;
	
	private OnClientTimeoutListener listener;
	
	public ConnectivityWatchdogThread(OnClientTimeoutListener listener) {
		this.listener = listener;
	}

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		startTime = System.currentTimeMillis();
		synchronized (lock) {
			while(!cancelled) {
				ArrayList<ClientId> clientList = responseTimeoutToClientId.remove(currentSecond);
				if(clientList != null) {
					for(ClientId client : clientList) {
						listener.onClientTimeout(client);
						clientIdToTimeout.remove(client);
					}
				}
				++currentSecond;
				long nextWakeTime = startTime + currentSecond * 1000;
				long sleepTime = nextWakeTime - System.currentTimeMillis();
				if(sleepTime < 0) {
					System.out.println("ConnectivityWatchdogThread loop took longer than one second.  Resuming immediately.");
				}
				try {
					lock.wait(Math.max(0, sleepTime));
				} catch (InterruptedException e) {}
			}
		}
	}

	public void setTimeout(ClientId client, int timeoutDelaySeconds) {
		long timeout = currentSecond + 1 + timeoutDelaySeconds;
		synchronized(lock) {
			if(clientIdToTimeout.containsKey(client)) {
				return;
			}
			if(!responseTimeoutToClientId.containsKey(timeout)) {
				responseTimeoutToClientId.put(timeout, new ArrayList<ClientId>());
			}
			responseTimeoutToClientId.get(timeout).add(client);
			clientIdToTimeout.put(client, timeout);
		}
	}

	public boolean cancelTimeout(ClientId clientId) {
		synchronized (lock) {
			if(!clientIdToTimeout.containsKey(clientId)) return false;
			long timeout = clientIdToTimeout.remove(clientId);
			ArrayList<ClientId> clientList = responseTimeoutToClientId.get(timeout); 
			clientList.remove(clientId);
			if(clientList.isEmpty()) {
				responseTimeoutToClientId.remove(timeout);
			}
			return true;
		}
	}

	public void cancel() {
		synchronized (lock) {
			cancelled = true;
			lock.notify();
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		this.listener = null;
	}

	public interface OnClientTimeoutListener {
		
		public void onClientTimeout(ClientId client);
		
	}

}
