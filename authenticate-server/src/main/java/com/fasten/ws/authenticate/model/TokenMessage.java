package com.fasten.ws.authenticate.model;

public class TokenMessage extends AbstractMessage<TokenModel>{
	private TokenModel token;
	
	{
		setType(MessageTypes.CUSTOMER_API_TOKEN_TYPE);
	}
	
	public TokenMessage() {
	}
	
	@Override
	public TokenModel getData() {
		return token;
	}

	@Override
	public void setData(TokenModel token) {
		this.token = token;
	}
	
}
