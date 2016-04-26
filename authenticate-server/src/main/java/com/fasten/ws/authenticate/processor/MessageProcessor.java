package com.fasten.ws.authenticate.processor;

import javax.websocket.Session;

import com.fasten.ws.authenticate.db.AuthenticateDAO;
import com.fasten.ws.authenticate.exceptions.MessageProcessorException;
import com.fasten.ws.authenticate.model.Message;
import com.fasten.ws.authenticate.processor.handler.Callback;

public interface MessageProcessor<M, S, E> {

	void process(M message) throws MessageProcessorException;
	MessageProcessor<M, S, E> withAuthenticateDao(AuthenticateDAO dao);
	MessageProcessor<M, S, E> forSession(Session session);
	MessageProcessor<M, S, E> addSuccessCallback(Callback<S> handler);
	MessageProcessor<M, S, E> addErrorCallback(Callback<E> handler);
}
