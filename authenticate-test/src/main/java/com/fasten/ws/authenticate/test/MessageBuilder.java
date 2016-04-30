package com.fasten.ws.authenticate.test;

import java.util.UUID;



public class MessageBuilder {
	
	public static String buildLoginCustomerMessage(String email, String password) {
		String result = "{\"type\":\"LOGIN_CUSTOMER\","
						+ "\"sequence_id\":\"" + UUID.randomUUID() + "\","
						+ "\"data\":{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}"
				+ "}";
		return result;
	}
	
	
	public static String buildRegisterCustomerMessage(String email, String password) {
		String result = "{\"type\":\"REGISTER_CUSTOMER\","
						+ "\"sequence_id\":\"" + UUID.randomUUID() + "\","
						+ "\"data\":{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}"
				+ "}";
		return result;
	}
	public static String buildUnregisterCustomerMessage(String email, String password) {
		String result = "{\"type\":\"UNREGISTER_CUSTOMER\","
						+ "\"sequence_id\":\"" + UUID.randomUUID() + "\","
						+ "\"data\":{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}"
				+ "}";
		return result;
	}
}
