package com.fasten.ws.authenticate.test;

import java.net.URI;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class Task implements Callable<String> {
	private User user;
	private StringBuilder log = new StringBuilder();
	private String nl = System.getProperty("line.separator");
	public Task(User user) {
		this.user = user;
	}
	
	@Override
	public String call() throws Exception {
		Long taskStart = System.currentTimeMillis();
		log.append("*****" + nl + "email: "+ user.getEmail() + nl + "password: " + user.getPassword() + nl + "[" + nl);
		MyClientEndpoint ep = new MyClientEndpoint(new URI("ws://localhost:8080/wsauhenticate/auth"), log);
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
			log.append("task start at: " + new Date(taskStart) + nl);
			log.append("task end at: " + new Date(tasklEnd) + nl);
			log.append("task time : " + TimeUnit.MILLISECONDS.toSeconds((tasklEnd - taskStart))+ " sec" + nl);
			log.append("task time : " + (tasklEnd - taskStart) + " msec" + nl);
			log.append("]");
		}
		return log.toString();
	}

	private void sendMessage(MyClientEndpoint ep, String message, CountDownLatch letch) {
		ep.sendMessage(message, letch);
		try {
			letch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
