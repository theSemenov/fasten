package com.fasten.ws.authenticate.processor.handler;

import java.io.IOException;
import java.util.List;

import javax.websocket.EncodeException;
import javax.websocket.Session;

import com.fasten.ws.authenticate.model.Message;
import com.fasten.ws.authenticate.model.TokenMessage;

public class AuthenticateSuccessCallback<K extends Message<?>> implements Callback<K> {

	@Override
	public void call(K data, List<Session> sessions) {
		defaultCall(data, sessions);
	}

	

}
