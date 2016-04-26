package com.fasten.ws.authenticate.processor;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.websocket.Session;

import com.fasten.ws.authenticate.db.AuthenticateDAO;
import com.fasten.ws.authenticate.exceptions.AuthenticateServiceException;
import com.fasten.ws.authenticate.exceptions.MessageProcessorException;
import com.fasten.ws.authenticate.model.ErrorMessage;
import com.fasten.ws.authenticate.model.ErrorModel;
import com.fasten.ws.authenticate.model.LoginMessage;
import com.fasten.ws.authenticate.model.LoginModel;
import com.fasten.ws.authenticate.model.Message;
import com.fasten.ws.authenticate.model.MessageTypes;
import com.fasten.ws.authenticate.model.TokenMessage;
import com.fasten.ws.authenticate.model.TokenModel;
import com.fasten.ws.authenticate.processor.handler.Callback;

public class AuthenticateProcessor<M extends Message<?>> implements
		MessageProcessor<M, TokenMessage, ErrorMessage> {
	private List<Callback<ErrorMessage>> errorCallbacks = new ArrayList<Callback<ErrorMessage>>();
	private List<Callback<TokenMessage>> successCallbacks = new ArrayList<Callback<TokenMessage>>();
	private Set<Session> sessions = new HashSet<Session>();
	private AuthenticateDAO dao;

	@Override
	public MessageProcessor<M, TokenMessage, ErrorMessage> addSuccessCallback(
			Callback<TokenMessage> handler) {
		successCallbacks.add(handler);
		return this;
	}

	@Override
	public MessageProcessor<M, TokenMessage, ErrorMessage> addErrorCallback(
			Callback<ErrorMessage> handler) {
		errorCallbacks.add(handler);
		return this;
	}

	@Override
	public void process(M message) throws MessageProcessorException {
		try {
			if (message == null) {
				throw new MessageProcessorException("message is null",
						"message.is.null");
			}
			if (!message.getType().equals(MessageTypes.LOGIN_CUSTOMER_TYPE)) {
				throw new MessageProcessorException("message not suport",
						"message.not.suport");
			}
			if (!(message instanceof LoginMessage)) {
				throw new MessageProcessorException("message not suport",
						"message.not.suport");
			}

			if(dao == null) {
				throw new MessageProcessorException("authenticate dao is null",
						"authenticate.dao.is.null");
			}
			validateMessage(message);
			LoginMessage loginMessage = (LoginMessage) message;
			LoginModel data = loginMessage.getData();

			TokenModel token = dao.authenticate(data.getEamil(),
					data.getPassword());
			if (token == null) {

			}
			TokenMessage tm = new TokenMessage();
			tm.setSequenceId(loginMessage.getSequenceId());
			tm.setData(token);
			for (Callback<TokenMessage> calback : successCallbacks) {
				calback.call(tm, new ArrayList<Session>(sessions));
			}

		} catch (AuthenticateServiceException e) {
			processException(e);
			throw new MessageProcessorException("some exception", "some.exception", e);
		} catch (Exception e) {
			throw new MessageProcessorException("some exception", "some.exception", e);

		}
	}

	private void processException(AuthenticateServiceException e) {
		for (Callback<ErrorMessage> calback : errorCallbacks) {
			ErrorMessage message = new ErrorMessage(new ErrorModel(e));
			calback.call(message, new ArrayList<Session>(sessions));
		}
	}

	private void validateMessage(M message) throws MessageProcessorException {

		LoginMessage loginMessage = (LoginMessage) message;
		String sequenceId = message.getSequenceId();
		if (sequenceId == null) {
			throw new MessageProcessorException("sequenceId is empty",
					"sequenceId.is.empty");
		}
		LoginModel lm = loginMessage.getData();
		if (lm == null) {
			throw new MessageProcessorException("login data is empty",
					"login.data.is.empty");
		}
		String email = lm.getEamil();
		if (email == null || email.isEmpty()) {
			throw new MessageProcessorException("email is empty",
					"email.data.is.empty");
		}
		String password = lm.getPassword();
		if (password == null || password.isEmpty()) {
			throw new MessageProcessorException("email is empty",
					"email.data.is.empty");
		}
	}

	@Override
	public MessageProcessor<M, TokenMessage, ErrorMessage> forSession(
			Session session) {
		sessions.add(session);
		return this;
	}

	@Override
	public MessageProcessor<M, TokenMessage, ErrorMessage> withAuthenticateDao(
			AuthenticateDAO dao) {
		this.dao = dao;
		return this;
	}

}
