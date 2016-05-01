package com.fasten.ws.authenticate.test;

import java.net.URI;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Task implements Callable<String> {
	private User user;
	private String endpointUri;
	private StringBuilder trace = new StringBuilder();
	private String nl = System.getProperty("line.separator");
	private static Logger _log = LoggerFactory.getLogger(Task.class);

	public Task(User user, String endpointUri) {
		this.user = user;
		if(endpointUri == null || endpointUri.isEmpty()) {
			throw new NullPointerException("endpointUri can not be null or empty");
		}
		this.endpointUri = endpointUri;
	}
	
	@Override
	public String call() throws Exception {
		Long taskStart = System.currentTimeMillis();
		trace.append("*****" + nl + "email: "+ user.getEmail() + nl + "password: " + user.getPassword() + nl + "[" + nl);
		MyClientEndpoint ep = new MyClientEndpoint(new URI(endpointUri), trace);
		try{
			String messageReg = MessageBuilder.buildRegisterCustomerMessage(
					user.getEmail(), user.getPassword());
			sendMessage(ep, messageReg, new CountDownLatch(1));	

			String message = MessageBuilder.buildLoginCustomerMessage(
					user.getEmail(), user.getPassword());
			sendMessage(ep, message, new CountDownLatch(1));
		} finally {
			String messageUnreg = MessageBuilder.buildUnregisterCustomerMessage(
					user.getEmail(), user.getPassword());
			sendMessage(ep, messageUnreg, new CountDownLatch(1));
			Long tasklEnd = System.currentTimeMillis();
			trace.append("task start at: " + new Date(taskStart) + nl);
			trace.append("task end at: " + new Date(tasklEnd) + nl);
			trace.append("task time : " + TimeUnit.MILLISECONDS.toSeconds((tasklEnd - taskStart))+ " sec" + nl);
			trace.append("task time : " + (tasklEnd - taskStart) + " msec" + nl);
			trace.append("]");
			ep.closeSession();
		}
		return trace.toString();
	}

	private void sendMessage(MyClientEndpoint ep, String message, CountDownLatch letch) {
		ep.sendMessage(message, letch);
		try {
			letch.await();
		} catch (InterruptedException e) {
			_log.error(e.getMessage(), e);
		}
	}
}
