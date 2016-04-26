package com.fasten.ws.authenticate.model;

import com.google.gson.annotations.SerializedName;

public class LoginMessage extends AbstractMessage<LoginModel> {
	@SerializedName("data")
	private LoginModel data;

	{
		setType(MessageTypes.LOGIN_CUSTOMER_TYPE);
	}
	
	public LoginMessage() {
		
	}
	@Override
	public LoginModel getData() {
		return data;
	}

	@Override
	public void setData(LoginModel data) {
		this.data = data;
	}

}
