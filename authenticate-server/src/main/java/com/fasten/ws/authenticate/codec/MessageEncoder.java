package com.fasten.ws.authenticate.codec;

import java.io.IOException;
import java.text.ParseException;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasten.ws.authenticate.model.ErrorMessage;
import com.fasten.ws.authenticate.model.LoginMessage;
import com.fasten.ws.authenticate.model.Message;
import com.fasten.ws.authenticate.model.MessageTypes;
import com.fasten.ws.authenticate.model.TokenMessage;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

public class MessageEncoder implements Encoder.Text<Message<?>> {
	private Gson gson = ObjectFactory.getGson();
	private static Logger _log = LoggerFactory.getLogger(MessageEncoder.class);

	@Override
	public void init(EndpointConfig config) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String encode(Message<?> object) throws EncodeException {
		_log.info("start encoding");
		try {
			String json = gson.toJson(object);
			_log.info("encoded message encoding: " + json);
			return json;
		} catch (Exception e) {
			_log.error(e.getMessage(), e);
			throw e;
		}
	}
	

}
