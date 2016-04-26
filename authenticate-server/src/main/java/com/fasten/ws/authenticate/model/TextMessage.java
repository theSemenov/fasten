package com.fasten.ws.authenticate.model;

import com.google.gson.annotations.SerializedName;

public class TextMessage extends AbstractMessage<String> {
	@SerializedName("data")
	private String data;
	
	public TextMessage() {
		super();
	}
	public TextMessage(String data) {
		super();
		this.data = data;
	}

	@Override
	public String getData() {
		return data;
	}

	@Override
	public void setData(String data) {
		this.data = data;
		
	}

}
