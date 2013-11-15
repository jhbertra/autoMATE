package com.automate.models;

public class Node {

	public final String name;
	public final int id;
	public final String manufacturerCode;
	public final String model;
	public final String maxVersion;
	
	public Node(String name, int id, String manufacturerCode, String model,String maxVersion) {
		this.name = name;
		this.id = id;
		this.manufacturerCode = manufacturerCode;
		this.model = model;
		this.maxVersion = maxVersion;
	}
	
}
