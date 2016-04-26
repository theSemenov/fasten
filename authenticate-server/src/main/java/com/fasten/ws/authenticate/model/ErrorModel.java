package com.fasten.ws.authenticate.model;

import java.io.Serializable;

import com.fasten.ws.authenticate.exceptions.AuthenticateServiceException;
import com.fasten.ws.authenticate.exceptions.MessageProcessorException;

public class ErrorModel implements Serializable {
	private String errorDescription = "Customer not found";
	private String errorCode = "customer.notFound";

	public ErrorModel() {
		
	}

	public ErrorModel(AuthenticateServiceException e) {
		this.errorCode = e.getCode();
		this.errorDescription = e.getMessage();
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
