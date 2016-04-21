package com.fasten.ws.authenticate.model;

public class ErrorMessage extends AbstractMessage<ErrorModel> {
	private ErrorModel error;
	
	{
		setType(MessageTypes.CUSTOMER_ERROR_TYPE);
	}
	
	public ErrorMessage() {
		
	}
	
	@Override
	public ErrorModel getData() {
		return error;
	}

	@Override
	public void setData(ErrorModel error) {
		this.error = error;
		
	}

}
