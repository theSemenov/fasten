package com.fasten.ws.authenticate.codec;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ObjectFactory {
	public static Gson gson;
	
	public static Gson getGson() {
		if(gson == null) {
			gson = new GsonBuilder().setDateFormat("YYYY-MM-DD'T'HH:mm:ss'Z'").create();
		}
		return gson;
	}
}
