package com.fasten.ws.authenticate.processor.handler;

import java.util.List;

import javax.websocket.Session;

import com.fasten.ws.authenticate.model.TokenMessage;

public class AuthenticateSuccessCallback implements Callback<TokenMessage> {

	@Override
	public void call(TokenMessage data, List<Session> sessions) {
		
		
	}

}
