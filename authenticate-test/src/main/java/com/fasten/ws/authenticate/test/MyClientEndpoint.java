package com.fasten.ws.authenticate.test;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.CountDownLatch;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.CloseReason.CloseCodes;
import javax.websocket.ContainerProvider;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.OnOpen;
import javax.websocket.WebSocketContainer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ClientEndpoint
public class MyClientEndpoint {
	private static Logger _log = LoggerFactory.getLogger(Task.class);
	private Session userSession = null;
	private StringBuilder trace = null;
	private String nl = System.getProperty("line.separator");
	private CountDownLatch latch;

    public MyClientEndpoint(URI endpointURI, StringBuilder trace) {
    	this.trace = trace;
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, endpointURI);
        } catch (Exception e) {
        	_log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
    
	@OnOpen
	public void onOpen(Session userSession) {
		this.userSession = userSession;
		trace.append("connect open");
		trace.append(nl);
	}
	@OnClose
	public void onClose(Session userSession, CloseReason reason) {
		trace.append(nl);
		
		trace.append("connect close ");
		if (reason != null) {
			trace.append(reason.getCloseCode() + ": " + reason.getReasonPhrase());
		}
		trace.append(nl);
		latch.countDown();
	}
	
	@OnError
	public void onError(Session session, Throwable t) {
		trace.append(nl);
		trace.append("connect error");
		trace.append(nl);
		latch.countDown();
	}
	@OnMessage
    public void onMessage(Session userSession, String message) {
		trace.append("<-");
		trace.append(nl);
		trace.append(message);
		trace.append(nl);
		latch.countDown();
    }
	
	public void sendMessage(String message, CountDownLatch latch) {
		this.latch = latch;
		trace.append("->");
		trace.append(nl);
		trace.append(message);
		trace.append(nl);
		 try {
			this.userSession.getBasicRemote().sendText(message);
		} catch (IOException e) {
        	_log.error(e.getMessage(), e);
		};
	}

	public void closeSession() {
		if(userSession.isOpen()) {
			try {
				CloseReason reason = new CloseReason(CloseCodes.NORMAL_CLOSURE, "Task completed");
				userSession.close(reason);
			} catch (IOException e) {
	        	_log.error(e.getMessage(), e);
			}
		}
		
	}
}
