package com.fasten.ws.authenticate.model;

import java.io.Serializable;

public class ErrorModel implements Serializable {
	private String errorDescription = "Customer not found";
	private String errorCode = "customer.notFound";

	public ErrorModel() {
		
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
}