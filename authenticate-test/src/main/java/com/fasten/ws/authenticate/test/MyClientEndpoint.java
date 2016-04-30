package com.fasten.ws.authenticate.test;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.CountDownLatch;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.OnOpen;
import javax.websocket.WebSocketContainer;

@ClientEndpoint
public class MyClientEndpoint {
	private Session userSession = null;
	private StringBuilder log = null;
	private String nl = System.getProperty("line.separator");
	private CountDownLatch latch;

    public MyClientEndpoint(URI endpointURI, StringBuilder log) {
    	this.log = log;
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
		log.append("connect open");
		log.append(nl);
	}
	@OnClose
	public void onClose(Session userSession, CloseReason reason) {
		log.append("connect close");
		log.append(nl);
		latch.countDown();
	}
	
	@OnError
	public void onError(Session session, Throwable t) {
		log.append("connect error");
		log.append(nl);
		latch.countDown();
	}
	@OnMessage
    public void onMessage(Session userSession, String message) {
		log.append("<-");
		log.append(nl);
		log.append(message);
		log.append(nl);
		latch.countDown();
    }
	
	public void sendMessage(String message, CountDownLatch latch) {
		this.latch = latch;
		log.append("->");
		log.append(nl);
		log.append(message);
		log.append(nl);
		 try {
			this.userSession.getBasicRemote().sendText(message);
		} catch (IOException e) {
			e.printStackTrace();
		};
	}
}
