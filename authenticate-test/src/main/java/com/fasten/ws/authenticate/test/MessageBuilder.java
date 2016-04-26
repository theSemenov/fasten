package com.fasten.ws.authenticate.test;



public class MessageBuilder {
	
	public static String buildLoginCustomerMessage(String email, String password) {
		String result = "{\"type\":\"LOGIN_CUSTOMER\","
						+ "\"sequence_id\":\"a29e4fd0-581d-e06b-c837-4f5f4be7dd18\","
						+ "\"data\":{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}"
				+ "}";
		return result;
	}
	
	

}
