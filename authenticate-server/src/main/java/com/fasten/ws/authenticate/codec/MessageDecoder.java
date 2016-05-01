package com.fasten.ws.authenticate.codec;

import java.io.IOException;
import java.text.ParseException;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasten.ws.authenticate.AuthenticateEndpoint;
import com.fasten.ws.authenticate.model.ErrorMessage;
import com.fasten.ws.authenticate.model.LoginMessage;
import com.fasten.ws.authenticate.model.Message;
import com.fasten.ws.authenticate.model.MessageTypes;
import com.fasten.ws.authenticate.model.ObjectMessage;
import com.fasten.ws.authenticate.model.RegisterMessage;
import com.fasten.ws.authenticate.model.TokenMessage;
import com.fasten.ws.authenticate.model.UnregisterMessage;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

public class MessageDecoder implements Decoder.Text<Message<?>> {
	private Gson gson = ObjectFactory.getGson();
	private static Logger _log = LoggerFactory.getLogger(MessageDecoder.class);

	@Override
	public void init(EndpointConfig config) {
	}

	@Override
	public void destroy() {
	}

	@Override
	public Message<?> decode(String s) throws DecodeException {

		try {
			JsonObject jsonObj = gson.fromJson(s, JsonObject.class);
			String type = jsonObj.get("type").getAsString();
			_log.error("incoming message with type: " + type);
			if (MessageTypes.LOGIN_CUSTOMER_TYPE.equals(type)) {
				return decodeMessage(s, LoginMessage.class);
			}
			if (MessageTypes.CUSTOMER_API_TOKEN_TYPE.equals(type)) {
				return decodeMessage(s, TokenMessage.class);
			}
			if (MessageTypes.CUSTOMER_ERROR_TYPE.equals(type)) {
				return decodeMessage(s, ErrorMessage.class);
			}
			if (MessageTypes.REGISTER_CUSTOMER_TYPE.equals(type)) {
				return decodeMessage(s, RegisterMessage.class);
			}
			if (MessageTypes.UNREGISTER_CUSTOMER_TYPE.equals(type)) {
				return decodeMessage(s, UnregisterMessage.class);
			}
			try {
				return decodeMessage(s, ObjectMessage.class);
			} catch (Exception e) {
				_log.info(e.getMessage(), e);
			}
		} catch (JsonSyntaxException e) {
			_log.error(e.getMessage(), e);
			throw new DecodeException(s, "string is not json");
		}
		_log.error(s + " is not suported type");
		throw new DecodeException(s, "not suported type");

	}

	private Message<?> decodeMessage(String s, Class<? extends Message<?>> clazz)
			throws DecodeException {
		Message<?> obj = null;
		try {
			obj = gson.fromJson(s, clazz);
		} catch (Exception e) {
			_log.error(e.getMessage(), e);
			throw new DecodeException(s, "", e);
		}
		return obj;
	}

	@Override
	public boolean willDecode(String s) {
		try {
			JsonObject jsonObj = gson.fromJson(s, JsonObject.class);
			String type = jsonObj.get("type").getAsString();
			if (type == null) {
				_log.error("type not set in message " + s);
				return false;
			}
		} catch (JsonSyntaxException e) {
			_log.error("string is not json " + s);
			return false;
		}
		return true;
	}

}
