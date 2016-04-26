package com.fasten.ws.authenticate.exceptions;

public class AuthenticateServiceException extends Exception {

		private String code;
		
		public AuthenticateServiceException() {
			super();
		}
		
		public AuthenticateServiceException(String message, String code) {
			super(message);
			this.code = code;
		}


		public AuthenticateServiceException(String message, Throwable cause,
				boolean enableSuppression, boolean writableStackTrace) {
			super(message, cause, enableSuppression, writableStackTrace);
		}

		public AuthenticateServiceException(String message, Throwable cause) {
			super(message, cause);
		}

		public AuthenticateServiceException(String message) {
			super(message);
		}

		public AuthenticateServiceException(Throwable cause) {
			super(cause);
			// TODO Auto-generated constructor stub
		}

		public AuthenticateServiceException(String message, String code, Exception e) {
			super(message, e);
			this.code = code;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

	}