package com.fasten.ws.authenticate.processor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.websocket.Session;

import com.fasten.ws.authenticate.db.AuthenticateDAO;
import com.fasten.ws.authenticate.exceptions.AuthenticateServiceException;
import com.fasten.ws.authenticate.exceptions.DatabaseException;
import com.fasten.ws.authenticate.exceptions.MessageProcessorException;
import com.fasten.ws.authenticate.model.ErrorMessage;
import com.fasten.ws.authenticate.model.ErrorModel;
import com.fasten.ws.authenticate.model.LoginMessage;
import com.fasten.ws.authenticate.model.LoginModel;
import com.fasten.ws.authenticate.model.Message;
import com.fasten.ws.authenticate.model.MessageTypes;
import com.fasten.ws.authenticate.model.RegisterMessage;
import com.fasten.ws.authenticate.model.ResultOperationMessage;
import com.fasten.ws.authenticate.model.TokenMessage;
import com.fasten.ws.authenticate.model.TokenModel;
import com.fasten.ws.authenticate.model.UnregisterMessage;
import com.fasten.ws.authenticate.processor.handler.AuthenticateErrorCllback;
import com.fasten.ws.authenticate.processor.handler.Callback;

@Stateless
public class AuthenticateProcessor<M extends Message<?>> implements
		MessageProcessor<M> {
	private List<Callback<ErrorMessage>> errorCallbacks = new ArrayList<Callback<ErrorMessage>>();
	private List<Callback<? extends Message<?>>> successCallbacks = new ArrayList<Callback<? extends Message<?>>>();
	private List<String> supportedMessages = new ArrayList<String>() {{
		add(MessageTypes.LOGIN_CUSTOMER_TYPE);
		add(MessageTypes.REGISTER_CUSTOMER_TYPE);
		add(MessageTypes.UNREGISTER_CUSTOMER_TYPE);
	}};
	private Set<Session> sessions = new HashSet<Session>();
	private AuthenticateDAO dao;



	@Override
	public MessageProcessor<M> addErrorCallback(
			AuthenticateErrorCllback handler) {
		errorCallbacks.add(handler);
		return this;
	}

	@Override
	public void process(M message) throws MessageProcessorException {

		String sequenceId = null;
		try {
			if (message == null) {
				throw new MessageProcessorException("message is null",
						"message.is.null");
			}
			String messageType = message.getType();
			sequenceId = message.getSequenceId();
			if (!supportedMessages.contains(message.getType())) {
				throw new MessageProcessorException("message not suport",
						"message.not.suport");
			}
			checkType(message);

			if(dao == null) {
				throw new MessageProcessorException("authenticate dao is null",
						"authenticate.dao.is.null");
			}
			validateMessage(message);
			if (MessageTypes.LOGIN_CUSTOMER_TYPE.equals(messageType)){
				login(message);
			}
			if (MessageTypes.REGISTER_CUSTOMER_TYPE.equals(messageType)){
				register(message);
			}
			if (MessageTypes.UNREGISTER_CUSTOMER_TYPE.equals(messageType)){
				unregister(message);
			}

		} catch (AuthenticateServiceException e) {
			processException(e, sequenceId);
		} catch (Exception e) {
			MessageProcessorException me = new MessageProcessorException("some exception", "some.exception", e);
			processException(me, sequenceId);
			throw me;

		}
	
	}

	private void closeSessions() {
		for(Session session : sessions){
			closeSession(session);
		}
	}

	private void closeSession(Session session) {
		if(session != null && session.isOpen()) {
			try {
				session.getBasicRemote().flushBatch();
				session.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void unregister(M message) throws DatabaseException {
		UnregisterMessage unregisterMessage = (UnregisterMessage) message;
		LoginModel data = unregisterMessage.getData();
		boolean result = dao.delete(data.getEamil(), data.getPassword());
		ResultOperationMessage returnMessage = new ResultOperationMessage("success");
		for (Callback calback : successCallbacks) {
			calback.call(returnMessage, new ArrayList<Session>(sessions));
		}
	}

	private void register(M message) throws DatabaseException {
		RegisterMessage registerMessage = (RegisterMessage) message;
		LoginModel data = registerMessage.getData();
		LoginModel lm = dao.register(data.getEamil(), data.getPassword());
		ResultOperationMessage returnMessage = new ResultOperationMessage("success");
		for (Callback calback : successCallbacks) {
			calback.call(returnMessage, new ArrayList<Session>(sessions));
		}
	}

	private void login(M message) throws DatabaseException {
		LoginMessage loginMessage = (LoginMessage) message;
		LoginModel data = loginMessage.getData();
		TokenModel token = dao.authenticate(data.getEamil(),
				data.getPassword());
		if (token == null) {

		}
		TokenMessage tm = new TokenMessage();
		tm.setSequenceId(loginMessage.getSequenceId());
		tm.setData(token);
		for (Callback calback : successCallbacks) {
			calback.call(tm, new ArrayList<Session>(sessions));
		}
	}

	private void checkType(M message) throws MessageProcessorException {
		boolean result = false;
		if (message instanceof LoginMessage) {
			result = true;
		}
		if(!result) {
			throw new MessageProcessorException("message not suport",
					"message.not.suport");
		}
	}

	private void processException(AuthenticateServiceException e, String sequenceId) {
		for (Callback<ErrorMessage> calback : errorCallbacks) {
			ErrorMessage message = new ErrorMessage(new ErrorModel(e));
			message.setSequenceId(sequenceId);
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
	public MessageProcessor<M> forSession(
			Session session) {
		sessions.add(session);
		return this;
	}

	@Override
	public MessageProcessor<M> withAuthenticateDao(
			AuthenticateDAO dao) {
		this.dao = dao;
		return this;
	}

	@Override
	public MessageProcessor<M> addSuccessCallback(
			Callback<? extends Message<?>> handler) {
		successCallbacks.add(handler);
		return this;
	}

	
}
