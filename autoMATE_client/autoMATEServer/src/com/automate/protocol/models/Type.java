package com.automate.protocol.models;

public enum Type {
	STRING,
	INTEGER,
	REAL,
	BOOLEAN,
	PERCENT,;

	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return super.toString().toLowerCase();
	}
}