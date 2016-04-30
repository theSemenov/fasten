package com.fasten.ws.authenticate.test;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

public class SimpleEchoClient {
	private Integer numberTask = 500;
	private Integer completedTask = 0;
	private Integer treadCount = 9;
	private String nl = System.getProperty("line.separator");

	public static void main(String[] args) throws URISyntaxException,
			InterruptedException {
		SimpleEchoClient echo = new SimpleEchoClient();
		echo.start();
	}

	private void start() {
		Long globalStart =System.currentTimeMillis();
		ThreadFactory threadFactory = new ThreadFactoryBuilder().setDaemon(true).build();
		ListeningExecutorService service = MoreExecutors
				.listeningDecorator(Executors.newFixedThreadPool(treadCount, threadFactory));

		List<User> users = UserGenerator.generateUsers(numberTask+1);
		for (User user : users) {
			ListenableFuture<String> future = service.submit(new Task(user));
			Futures.addCallback(future, new FutureCallback<String>() {

				@Override
				public void onFailure(Throwable arg0) {
					System.out.print(nl + "Task " + completedTask + "complete with error" + nl);
					completedTask++;
				}

				@Override
				public void onSuccess(String log) {
					System.out.print(nl + "Task " + completedTask + "complete" + nl);
					System.out.print(log);
					completedTask++;
				}
			});
		}
		while(completedTask < numberTask) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Long globalEnd =System.currentTimeMillis();
		System.out.print(nl + "============" + nl);
		System.out.print("global start at: " + new Date(globalStart) + nl);
		System.out.print("global end at: " + new Date(globalEnd) + nl);
		System.out.print("global tme : " + TimeUnit.MILLISECONDS.toSeconds((globalEnd - globalStart)) + " sec" + nl);
		System.out.print("global tme : " + (globalEnd - globalStart) + " msec" + nl);
	}
}
