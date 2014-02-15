package com.automate.protocol;

public class MessageFormatException extends Exception {

	public MessageFormatException() {
		super();
	}

	public MessageFormatException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public MessageFormatException(String detailMessage) {
		super(detailMessage);
	}

	public MessageFormatException(Throwable throwable) {
		super(throwable);
	}

}
