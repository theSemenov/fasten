package com.fasten.ws.authenticate.model;

public class RegisterMessage extends LoginMessage {

	{
		setType(MessageTypes.REGISTER_CUSTOMER_TYPE);
	}

	public RegisterMessage() {
		super();
	}
}
