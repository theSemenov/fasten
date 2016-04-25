package com.fasten.ws.authenticate.test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;



public class SimpleEchoClient {
	  public static void main(String[] args) throws URISyntaxException, InterruptedException {
		  MyClientEndpoint ep = new MyClientEndpoint(new URI("ws://localhost:8080/wsauhenticate/auth"));
		  ep.sendMessage("m");
		  Thread.sleep(1000);
	    }
}
