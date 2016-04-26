package com.fasten.ws.authenticate.processor;

import javax.websocket.Session;

import com.fasten.ws.authenticate.db.AuthenticateDAO;
import com.fasten.ws.authenticate.exceptions.MessageProcessorException;
import com.fasten.ws.authenticate.model.Message;
import com.fasten.ws.authenticate.processor.handler.AuthenticateErrorCllback;
import com.fasten.ws.authenticate.processor.handler.AuthenticateSuccessCallback;
import com.fasten.ws.authenticate.processor.handler.Callback;

public interface MessageProcessor<M> {

	void process(M message) throws MessageProcessorException;
	MessageProcessor<M> withAuthenticateDao(AuthenticateDAO dao);
	MessageProcessor<M> forSession(Session session);
	MessageProcessor<M> addSuccessCallback(Callback<? extends Message<?>> handler);
	MessageProcessor<M> addErrorCallback(AuthenticateErrorCllback handler);
}
