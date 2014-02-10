package com.automate.server;

import com.automate.server.connectivity.ConnectivityEngine;
import com.automate.server.connectivity.IConnectivityManager;
import com.automate.server.database.DatabaseManager;
import com.automate.server.database.IDatabaseManager;
import com.automate.server.messaging.IMessageManager;
import com.automate.server.messaging.MessageManager;
import com.automate.server.security.ISecurityManager;
import com.automate.server.security.SecurityManagerImpl;
import com.automate.server.security.SessionManager;


public class AutomateServer {

	/*
	 * The subsystems
	 */
	private IMessageManager messageManager;
	private IConnectivityManager connectivityManager;
	private ISecurityManager securityManager;
	private IDatabaseManager dbManager;
	
	private boolean started = false;
	
	private static final int MAJOR_VERSION = 0;
	private static final int MINOR_VERSION = 0;
	
	private Api api;
	
	public void initSubsystems() {
		dbManager = new DatabaseManager(3306, "localhost", "root", "quinoa");
		SessionManager sessionManager = new SessionManager(MAJOR_VERSION, MINOR_VERSION);
		securityManager = new SecurityManagerImpl(sessionManager, dbManager, MAJOR_VERSION, MINOR_VERSION);
		connectivityManager = new ConnectivityEngine(30, 15, sessionManager);
		messageManager = new MessageManager(dbManager, securityManager, connectivityManager);
		
		this.api = new Api();
		
		try {
			dbManager.initialize();
			securityManager.initialize();
			connectivityManager.initialize();
			messageManager.initialize();
		} catch(Exception e) {
			System.out.println("Error initializing managers.");
			e.printStackTrace();
		}
	}
	
	public void start() {
		if(!started) {
			try {
				dbManager.start();
				securityManager.start();
				connectivityManager.start();
				messageManager.start();
			} catch (Exception e) {
				System.out.println("Error starting server");
				e.printStackTrace();
			}
		} else {
			throw new IllegalStateException("Cannot start server twice!");
		}
	}
	
	public void shutdown() {
		if(started) {
			try {
				dbManager.terminate();
				securityManager.terminate();
				connectivityManager.terminate();
				messageManager.terminate();
			} catch (Exception e) {
				System.out.println("Error stopping server");
				e.printStackTrace();
			}
		} else {
			throw new IllegalStateException("Server not started, cannot shut down!");
		}
	}
	
	/**
	 * @return the api
	 */
	public Api getApi() {
		return api;
	}



	public class Api {
		
		public void shutdown() {
			AutomateServer.this.shutdown();
		}
		
	}
	
}
