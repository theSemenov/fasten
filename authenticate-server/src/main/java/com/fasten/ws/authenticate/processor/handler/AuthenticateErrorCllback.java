package com.fasten.ws.authenticate.processor.handler;

import java.io.IOException;
import java.util.List;

import javax.websocket.EncodeException;
import javax.websocket.Session;

import com.fasten.ws.authenticate.model.ErrorMessage;
import com.fasten.ws.authenticate.model.ErrorModel;

public class AuthenticateErrorCllback implements Callback<ErrorMessage> {

	@Override
	public void call(ErrorMessage data, List<Session> sessions) {
		defaultCall(data, sessions);
	}

}
