package com.fasten.ws.authenticate.model;

public class UnregisterMessage extends LoginMessage {
	{
		setType(MessageTypes.UNREGISTER_CUSTOMER_TYPE);
	}

	public UnregisterMessage() {
		super();
	}
}
