package com.fasten.ws.authenticate.db;

import com.fasten.ws.authenticate.model.LoginModel;

public interface AuthenticateDAO {

	LoginModel authenticate(String email, String password);
	LoginModel checkAuthenticate(String email, String password);
	LoginModel updateExpireDate(String token);
	LoginModel checkToken(String token);
	LoginModel expireToken(String token);
}
