package com.fasten.ws.authenticate.model;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class ResultOperationModel implements Serializable {
	@SerializedName("result")
	private String result;


	public ResultOperationModel() {
		super();
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	
}
