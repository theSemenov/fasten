package com.fasten.ws.authenticate.model;

import com.google.gson.annotations.SerializedName;

public class ObjectMessage extends AbstractMessage<Object> {
	@SerializedName("data")
	public Object data;
	
	public ObjectMessage() {
		super();
	}

	@Override
	public Object getData() {
		return data;
	}

	@Override
	public void setData(Object data) {
		this.data = data;
	}

}
