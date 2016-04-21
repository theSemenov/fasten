package com.fasten.ws.authenticate.model;

import java.util.Date;

public class TokenModel {

	private String apiToken;
	private Date apiTokenExpirationDate;
	
	public TokenModel(){
		
	}

	public String getApiToken() {
		return apiToken;
	}

	public void setApiToken(String apiToken) {
		this.apiToken = apiToken;
	}

	public Date getApiTokenExpirationDate() {
		return apiTokenExpirationDate;
	}

	public void setApiTokenExpirationDate(Date apiTokenExpirationDate) {
		this.apiTokenExpirationDate = apiTokenExpirationDate;
	}
}
