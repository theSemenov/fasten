package com.fasten.ws.authenticate.processor;

import java.nio.channels.AcceptPendingException;

import javax.inject.Inject;

import com.fasten.ws.authenticate.model.Message;

public class MessageProcessorFactory {
	
	
	public MessageProcessor get() {
		return new AuthenticateProcessor<Message<?>>();
	}
}
