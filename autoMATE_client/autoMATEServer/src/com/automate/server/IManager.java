package com.automate.server;

import java.sql.SQLException;

public interface IManager {

	public void initialize() throws Exception;
	public void start() throws Exception;
	public void terminate() throws Exception;
	
}
