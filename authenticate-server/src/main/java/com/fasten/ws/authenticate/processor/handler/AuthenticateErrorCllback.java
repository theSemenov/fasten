package com.fasten.ws.authenticate.processor.handler;

import java.util.List;

import javax.websocket.Session;

import com.fasten.ws.authenticate.model.ErrorMessage;

public class AuthenticateErrorCllback implements Callback<ErrorMessage> {

	@Override
	public void call(ErrorMessage data, List<Session> sessions) {
	
	}

}
