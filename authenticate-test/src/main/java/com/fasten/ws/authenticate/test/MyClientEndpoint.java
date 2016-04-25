package com.fasten.ws.authenticate.test;

import java.io.IOException;
import java.net.URI;

import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.OnOpen;
import javax.websocket.WebSocketContainer;

@ClientEndpoint
public class MyClientEndpoint {
	Session userSession = null;

    public MyClientEndpoint(URI endpointURI) {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, endpointURI);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
	@OnOpen
	public void onOpen(Session userSession) {
		this.userSession = userSession;
		System.out.println("fg");
	}
	
	@OnMessage
    public void onMessage(Session userSession, String message) {
       System.out.println(message);
    }
	
	public void sendMessage(String message) {
		 try {
			this.userSession.getBasicRemote().sendText(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
	}
}
