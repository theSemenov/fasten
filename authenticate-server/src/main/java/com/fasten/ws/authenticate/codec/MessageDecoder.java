package com.fasten.ws.authenticate.codec;

import java.io.IOException;
import java.text.ParseException;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

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

	@Override
	public void init(EndpointConfig config) {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public Message<?> decode(String s) throws DecodeException {

		try {
			JsonObject jsonObj = gson.fromJson(s, JsonObject.class);
			String type = jsonObj.get("type").getAsString();
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
			}
		} catch (JsonSyntaxException e) {
			throw new DecodeException(s, "string is not json");
		}
		throw new DecodeException(s, "not suported type");

	}

	private Message<?> decodeMessage(String s, Class<? extends Message<?>> clazz)
			throws DecodeException {
		Message<?> obj = null;
		try {
			obj = gson.fromJson(s, clazz);
		} catch (Exception e) {
			e.printStackTrace();
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
//				_log.error("type not set in message " + s);
				return false;
			}
		} catch (JsonSyntaxException e) {
		//	_log.error("string is not json " + s);
			return false;
		}
		return true;
	}

}
