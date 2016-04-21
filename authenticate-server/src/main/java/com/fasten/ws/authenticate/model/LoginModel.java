package com.fasten.ws.authenticate.model;

import java.io.Serializable;

public class LoginModel implements Serializable {
	private String eamil;
	private String data;
	
	public LoginModel() {
		
	}

	public String getEamil() {
		return eamil;
	}

	public void setEamil(String eamil) {
		this.eamil = eamil;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
}
