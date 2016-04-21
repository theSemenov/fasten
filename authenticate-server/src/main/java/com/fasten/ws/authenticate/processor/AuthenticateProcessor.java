package com.fasten.ws.authenticate.processor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.websocket.Session;

import com.fasten.ws.authenticate.model.ErrorMessage;
import com.fasten.ws.authenticate.model.LoginMessage;
import com.fasten.ws.authenticate.model.Message;
import com.fasten.ws.authenticate.model.MessageTypes;
import com.fasten.ws.authenticate.model.TokenMessage;
import com.fasten.ws.authenticate.processor.handler.Callback;

public class AuthenticateProcessor<M extends Message<?>> implements MessageProcessor<M, TokenMessage, ErrorMessage> {
	List<Callback<ErrorMessage>> errorCallbacks = new ArrayList<Callback<ErrorMessage>>();
	List<Callback<TokenMessage>> successCallbacks = new ArrayList<Callback<TokenMessage>>();
	Set<Session> sessions = new HashSet<Session>();
	
	@Override
	public MessageProcessor<M, TokenMessage, ErrorMessage> addSuccessHandler(
			Callback<TokenMessage> handler) {
		successCallbacks.add(handler);
		return this;
	}

	@Override
	public MessageProcessor<M, TokenMessage, ErrorMessage> addErrorHandler(
			Callback<ErrorMessage> handler) {
		errorCallbacks.add(handler);
		return this;
	}

	@Override
	public void process(M message) throws MessageNotSuportException {
		if(message == null) {
			throw new NullPointerException();
		}
		if(message.getType().equals(MessageTypes.LOGIN_CUSTOMER_TYPE)) {
			throw new MessageNotSuportException();
		}
		if(!(message instanceof LoginMessage)) {
			throw new MessageNotSuportException();
		}
		
		LoginMessage loginMessage = (LoginMessage) message;
		try {
			loginMessage.getData().getEamil() ;
			TokenMessage tm = new TokenMessage();
			for(Callback<TokenMessage> calback : successCallbacks) {
				calback.call(tm, new ArrayList<Session>(sessions));
			}
		} catch (Exception e) {
			
		}
		
	}

	@Override
	public MessageProcessor<M, TokenMessage, ErrorMessage> forSession(
			Session session) {
		sessions.add(session);
		return this;
	}

}
