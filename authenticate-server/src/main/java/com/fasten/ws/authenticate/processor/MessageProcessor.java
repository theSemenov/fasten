package com.fasten.ws.authenticate.processor;

import javax.websocket.Session;

import com.fasten.ws.authenticate.model.Message;
import com.fasten.ws.authenticate.processor.handler.Callback;

public interface MessageProcessor<M, S, E> {

	void process(M message) throws MessageNotSuportException;
	MessageProcessor<M, S, E> forSession(Session session);
	MessageProcessor<M, S, E> addSuccessHandler(Callback<S> handler);
	MessageProcessor<M, S, E> addErrorHandler(Callback<E> handler);
}
