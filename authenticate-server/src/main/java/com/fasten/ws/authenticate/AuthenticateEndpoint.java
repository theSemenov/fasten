package com.fasten.ws.authenticate;

import javax.inject.Inject;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.fasten.ws.authenticate.model.ErrorMessage;
import com.fasten.ws.authenticate.model.LoginMessage;
import com.fasten.ws.authenticate.model.Message;
import com.fasten.ws.authenticate.model.TokenMessage;
import com.fasten.ws.authenticate.processor.AuthenticateProcessor;
import com.fasten.ws.authenticate.processor.MessageNotSuportException;
import com.fasten.ws.authenticate.processor.MessageProcessor;
import com.fasten.ws.authenticate.processor.handler.AuthenticateSuccessCallback;

@ServerEndpoint(value = "auth")
public class AuthenticateEndpoint {
	@Inject
	private MessageProcessor<Message<?>, TokenMessage, ErrorMessage> messageProcessor = new AuthenticateProcessor();
		
	
	@OnMessage
	public String onMessage(Message<?> message, Session session) throws MessageNotSuportException {
			messageProcessor.forSession(session).process(message);
			return message.getSequenceId() + " delivered";
		
	}
	
	@OnMessage
	public void onOpen(Session session) {
	}
	
	@OnClose
	public void onClose(CloseReason reason) {
		
	}
	
	@OnError
	public void onError(Session session) {
		
	}
}
