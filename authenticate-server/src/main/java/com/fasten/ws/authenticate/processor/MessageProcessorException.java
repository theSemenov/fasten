package com.fasten.ws.authenticate.processor;

public class MessageProcessorException extends Exception {

	private String code;
	
	public MessageProcessorException() {
		super();
	}
	
	public MessageProcessorException(String message, String code) {
		super(message);
		this.code = code;
	}


	public MessageProcessorException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MessageProcessorException(String message, Throwable cause) {
		super(message, cause);
	}

	public MessageProcessorException(String message) {
		super(message);
	}

	public MessageProcessorException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public MessageProcessorException(String message, String code, Exception e) {
		super(message, e);
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
