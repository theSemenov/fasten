package com.fasten.ws.authenticate.model;

import com.google.gson.annotations.SerializedName;

public class ErrorMessage extends AbstractMessage<ErrorModel> {
	@SerializedName("data")
	private ErrorModel data;
	
	{
		setType(MessageTypes.CUSTOMER_ERROR_TYPE);
	}
	
	public ErrorMessage() {
		
	}
	
	public ErrorMessage(ErrorModel data) {
		this.data = data;
	}
	
	@Override
	public ErrorModel getData() {
		return data;
	}

	@Override
	public void setData(ErrorModel data) {
		this.data = data;
		
	}

}
