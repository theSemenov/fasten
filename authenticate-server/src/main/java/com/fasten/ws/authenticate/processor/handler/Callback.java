package com.fasten.ws.authenticate.processor.handler;

import java.util.List;

import javax.websocket.Session;

public interface Callback<D> {

	void call(D data, List<Session> sessions);
}
