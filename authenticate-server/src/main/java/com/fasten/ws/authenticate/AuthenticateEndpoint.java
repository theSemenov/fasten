package com.fasten.ws.authenticate;

import java.io.IOException;

import javax.inject.Inject;
import javax.websocket.CloseReason;
import javax.websocket.DecodeException;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasten.ws.authenticate.codec.MessageDecoder;
import com.fasten.ws.authenticate.codec.MessageEncoder;
import com.fasten.ws.authenticate.db.AuthenticateDAO;
import com.fasten.ws.authenticate.exceptions.AuthenticateServiceException;
import com.fasten.ws.authenticate.exceptions.MessageProcessorException;
import com.fasten.ws.authenticate.model.ErrorMessage;
import com.fasten.ws.authenticate.model.ErrorModel;
import com.fasten.ws.authenticate.model.Message;
import com.fasten.ws.authenticate.processor.MessageProcessorFactory;
import com.fasten.ws.authenticate.processor.handler.AuthenticateErrorCllback;
import com.fasten.ws.authenticate.processor.handler.AuthenticateSuccessCallback;
 
@ServerEndpoint(value = "/auth", decoders={MessageDecoder.class}, encoders={MessageEncoder.class})
public class AuthenticateEndpoint {
	@Inject
	private MessageProcessorFactory processorFactory;
	@Inject
	private AuthenticateErrorCllback errorCllback;
	@Inject
	private AuthenticateSuccessCallback<?> successCllback;
	@Inject
	private AuthenticateDAO dao;
	private static Logger _log = LoggerFactory.getLogger(AuthenticateEndpoint.class);

	@SuppressWarnings("unchecked")
	@OnMessage
	public void onMessage(Session session, Message<?> message) throws MessageProcessorException {
		_log.info("[onOpen] start process");
		processorFactory.get().addErrorCallback(errorCllback)
						.addSuccessCallback(successCllback)
						.withAuthenticateDao(dao)
						.forSession(session)
						.process(message);
		_log.info("[onOpen] end process");
	}

	@OnOpen
	public void onOpen(Session session) {
		_log.info("[onOpen] ");
		
	}
	
	@OnClose
	public void onClose(CloseReason reason) {
		_log.info("[onClose] ");
		if (reason != null) {
			_log.info(reason.getCloseCode() + ": " + reason.getReasonPhrase());
		}
	}
	
	@OnError
	public void onError(Session session, Throwable e) {
		_log.error("[onError]", e);
		if(e instanceof DecodeException) {
			AuthenticateServiceException ae = new AuthenticateServiceException(e.getMessage(), "some.exception");
			ErrorMessage em = new ErrorMessage(new ErrorModel(ae));
			try {
				session.getBasicRemote().sendObject(em);
			} catch (IOException | EncodeException e1) {
				_log.error("" ,e);
			}
		}
	}
}
