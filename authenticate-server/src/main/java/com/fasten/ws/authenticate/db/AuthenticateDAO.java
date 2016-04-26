package com.fasten.ws.authenticate.db;

import com.fasten.ws.authenticate.db.entity.TokenEntity;
import com.fasten.ws.authenticate.exceptions.DatabaseException;
import com.fasten.ws.authenticate.model.LoginModel;
import com.fasten.ws.authenticate.model.TokenModel;

public interface AuthenticateDAO {

	TokenModel authenticate(String email, String password) throws DatabaseException;
	TokenEntity checkAuthenticate(String email) throws DatabaseException;
	void updateExpireDate(String token) throws DatabaseException;
	TokenEntity findToken(String token) throws DatabaseException;
	void expireToken(String token) throws DatabaseException;
}
