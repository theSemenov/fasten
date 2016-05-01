package com.fasten.ws.authenticate.test;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

public class SimpleEchoClient {
	private Integer numberTask = 10;
	private AtomicInteger completedTask = new AtomicInteger(-1);
	private Integer treadCount = 2;
	public StringBuilder trace = new StringBuilder();
	private String endpointUri = "ws://localhost:8080/wsauhenticate/auth";
	private String nl = System.getProperty("line.separator");
	private static Logger _log = LoggerFactory.getLogger(SimpleEchoClient.class);

	public static void main(String[] args) throws URISyntaxException,
			InterruptedException {
		
		SimpleEchoClient echo = new SimpleEchoClient();
		echo.start();
	}

	public SimpleEchoClient() {
		try {
			_log.info("load property file config.properties" );
			InputStream input = getBestLocation();
			Properties prop = new Properties();
			prop.load(input);
			numberTask = Integer.parseInt(prop.getProperty("numberTask"));
			treadCount = Integer.parseInt(prop.getProperty("treadCount"));
			endpointUri = prop.getProperty("endpointUri");
		} catch (IOException | NumberFormatException e) {
			_log.info("Error while parsing property file config.properties. Default property will used" );
		}
	}
	
	private void start() {
		_log.info("SimpleEchoClient start");
		_log.info("numberTask: " + numberTask);
		_log.info("threadCount: " + treadCount);
		_log.info("endpointUri: " + endpointUri);
		
		_log.info(nl + "TEST START");
		Long globalStart =System.currentTimeMillis();
		ThreadFactory threadFactory = new ThreadFactoryBuilder().setDaemon(true).build();
		ListeningExecutorService service = MoreExecutors
				.listeningDecorator(Executors.newFixedThreadPool(treadCount, threadFactory));
	
		List<User> users = UserGenerator.generateUsers(numberTask+1);
		for (User user : users) {
			ListenableFuture<String> future = service.submit(new Task(user, endpointUri));
			Futures.addCallback(future, new FutureCallback<String>() {
	
				@Override
				public void onFailure(Throwable arg0) {
					synchronized (this) {
						int complTask = completedTask.incrementAndGet();
						System.out.println("");
						trace.append(nl + "Task " + complTask + "complete with error" + nl);
					}
				}
	
				@Override
				public void onSuccess(String tr) {
					synchronized (this) {
						int complTask = completedTask.incrementAndGet();
						System.out.print(".");
						trace.append(nl + "Task " + complTask + " complete" + nl);
						trace.append(tr);
					}	
				}
			});
		}
		while(completedTask.get() < numberTask) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Long globalEnd =System.currentTimeMillis();
		_log.info(trace.toString());
		_log.info(nl + "============");
		_log.info("global start at: " + new Date(globalStart));
		_log.info("global end at: " + new Date(globalEnd));
		_log.info("global tme : " + TimeUnit.MILLISECONDS.toSeconds((globalEnd - globalStart)) + " sec" + nl);
		_log.info("global tme : " + (globalEnd - globalStart) + " msec");
		_log.info(nl + "TEST END");
	}

	private InputStream getBestLocation() {
		String location = this.getClass().getResource(this.getClass().getSimpleName() + ".class").toString();
		boolean isJar = checkIsJar(location);
		InputStream is = null;
		if(isJar) {
			_log.info("runing from jar");
			String uri = checkEndSymbol(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
			Path path = Paths.get(uri);
			String configPath = path.getParent().toString() + "\\config.properties";
			try {
				is = new FileInputStream(configPath);
			} catch (FileNotFoundException e) {
				_log.info("File not found in path: " + configPath);
			}
		}
		if(is == null) {
			_log.info("Load standart config file");
			is = this.getClass().getClassLoader().getResourceAsStream("config.properties");
		}
		return is;
	}

	private boolean checkIsJar(String location) {
		if(location.startsWith("jar:"))
			return true;
		return false;
	}

	private String checkEndSymbol(String path) {
		if(!"/".equals(path.substring(1, 2))) {
			path = path.substring(1, path.length());
		}; 
		return path;
	}
}
