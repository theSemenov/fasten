package com.fasten.ws.authenticate.model;

import com.google.gson.annotations.SerializedName;

public class TokenMessage extends AbstractMessage<TokenModel>{
	@SerializedName("data")
	private TokenModel data;
	
	{
		setType(MessageTypes.CUSTOMER_API_TOKEN_TYPE);
	}
	
	public TokenMessage() {
	}
	
	@Override
	public TokenModel getData() {
		return data;
	}

	@Override
	public void setData(TokenModel data) {
		this.data = data;
	}
	
}
