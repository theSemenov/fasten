package com.fasten.ws.authenticate;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "auth")
public class AuthenticateEndpoint {

	@OnMessage
	public String onMessage(String message) {
		return message;
		
	}
	
	@OnMessage
	public void onOpen(Session session) {
		
	}
	
	@OnClose
	public void onClose(CloseReason reason) {
		
	}
}
