package com.automate.server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Properties;

import com.automate.protocol.models.Node;
import com.automate.server.database.models.User;

public class DatabaseManager implements IDatabaseManager {
	
	private Connection connection;
	private Properties connectionProperties;
	private int hostport;
	private String hostname;
	
	public DatabaseManager(int i, String hostname, String username, String password) {
		this.hostport = i;
		this.hostname = hostname;
		this.connectionProperties = new Properties();
		this.connectionProperties.put("user", username);
		this.connectionProperties.put("password", password);
	}

	@Override
	public void initialize() throws Exception {
		connection = DriverManager.getConnection("jdbc:mysql://" + hostname + ":" + hostport + "/", connectionProperties);
	}

	@Override
	public void start() {
		//do nothing.
	}

	@Override
	public void terminate() throws Exception {
		connection.close();
	}

	@Override
	public List<Node> getClientNodeList(String username) {
		return null;
	}

	@Override
	public User getUser(String username) {
		return null;
	}

}
