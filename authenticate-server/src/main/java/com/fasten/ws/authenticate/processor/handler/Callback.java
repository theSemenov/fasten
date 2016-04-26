package com.fasten.ws.authenticate.processor.handler;

import java.io.IOException;
import java.util.List;

import javax.websocket.EncodeException;
import javax.websocket.Session;

public interface Callback<D> {

	void call(D data, List<Session> sessions);

	default void defaultCall(D data, List<Session> sessions) {
		for (Session session : sessions) {
			try {
				if (session.isOpen())
					session.getBasicRemote().sendObject(data);
			} catch (IOException | EncodeException e) {
				e.printStackTrace();
			}
		}
	}

}
