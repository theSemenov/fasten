package com.fasten.ws.authenticate.model;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class LoginModel implements Serializable {
	@SerializedName("email")
	private String eamil;
	@SerializedName("password")
	private String password;

	public LoginModel() {

	}

	public String getEamil() {
		return eamil;
	}

	public void setEamil(String eamil) {
		this.eamil = eamil;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
