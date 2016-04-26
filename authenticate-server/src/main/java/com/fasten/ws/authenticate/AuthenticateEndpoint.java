package com.fasten.ws.authenticate;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.fasten.ws.authenticate.codec.MessageDecoder;
import com.fasten.ws.authenticate.codec.MessageEncoder;
import com.fasten.ws.authenticate.db.AuthenticateDAO;
import com.fasten.ws.authenticate.exceptions.MessageProcessorException;
import com.fasten.ws.authenticate.model.ErrorMessage;
import com.fasten.ws.authenticate.model.LoginMessage;
import com.fasten.ws.authenticate.model.Message;
import com.fasten.ws.authenticate.model.TokenMessage;
import com.fasten.ws.authenticate.processor.AuthenticateProcessor;
import com.fasten.ws.authenticate.processor.MessageProcessor;
import com.fasten.ws.authenticate.processor.handler.AuthenticateErrorCllback;
import com.fasten.ws.authenticate.processor.handler.AuthenticateSuccessCallback;

@ServerEndpoint(value = "/auth", decoders={MessageDecoder.class}, encoders={MessageEncoder.class})
public class AuthenticateEndpoint {
	@Inject
	private MessageProcessor<Message<?>, TokenMessage, ErrorMessage> messageProcessor;
	
	@Inject
	private AuthenticateErrorCllback errorCllback;
	@Inject
	private AuthenticateSuccessCallback successCllback;
	@EJB
	private AuthenticateDAO dao;
	
	@OnMessage
	public void onMessage(Session session, Message<?> message) throws MessageProcessorException {
		System.out.println("Server[onMessage]: " + message);
		System.out.println(messageProcessor.getClass().toString());
		//return "[Hy bro]";
			
		messageProcessor
						.addErrorCallback(errorCllback)
						.addSuccessCallback(successCllback)
						.withAuthenticateDao(dao)
						.forSession(session)
						.process(message);
		
	}
	/*
	@OnOpen
	public String onOpen(Session session, String message) {
		System.out.println("Server[onOpen]: " + message);
		return message;
	}
*/
	@OnClose
	public void onClose(CloseReason reason) {
		
	}
	
	@OnError
	public void onError(Session session, Throwable e) {
		
	}
}
