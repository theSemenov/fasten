package com.fasten.ws.authenticate.db;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;

import com.fasten.ws.authenticate.db.entity.QueryNames;
import com.fasten.ws.authenticate.db.entity.TokenEntity;
import com.fasten.ws.authenticate.db.entity.UserEntity;
import com.fasten.ws.authenticate.db.entity.UserTokenHistoryEntity;
import com.fasten.ws.authenticate.exceptions.DatabaseException;
import com.fasten.ws.authenticate.model.LoginModel;
import com.fasten.ws.authenticate.model.TokenModel;

@Stateless
public class DefaultAuthenticateDAO implements AuthenticateDAO {
	@PersistenceUnit(name = "testDS")
	private EntityManager em;
	
	@Override
	public TokenModel authenticate(String email, String password) throws DatabaseException  {
		UserEntity user = findUser(email, password);
		TokenEntity token = checkAuthenticate(email);
		if(token != null) {
			deleteToken(token);
			addHistory("TOKEN_EXPIRE_RELOGIN", token);
		}
		TokenEntity newToken = newToken(user);
		addHistory("TOKEN_CREATED", newToken);
		TokenModel tm = new TokenModel();
		tm.setApiToken(newToken.getToken());
		tm.setApiTokenExpirationDate(newToken.getExpireDate());
		return tm;
	}



	private void addHistory(String action, TokenEntity token) {
		UserTokenHistoryEntity entity = new UserTokenHistoryEntity(action, token);
		em.persist(entity);
		
	}



	private TokenEntity newToken(UserEntity user) {
		Date loginDate = new Date();
		TokenEntity newToken = new TokenEntity();
		newToken.setUser(user);
		newToken.setToken(user.getEmail() + user.getPassword() + loginDate);
		newToken.setExpireDate(loginDate);
		newToken.setLoginDate(ttl(loginDate));
		em.persist(newToken);
		return newToken;
	}



	private UserEntity findUser(String email, String password) throws DatabaseException {
		TypedQuery<UserEntity> query = em.createNamedQuery(QueryNames.FIND_USER, UserEntity.class);
		query.setParameter("email", email);
		query.setParameter("password", password);
		try {
			UserEntity user = query.getResultList().get(0);
			return user;
		} catch (NullPointerException e) {
			throw new DatabaseException("user not found", "user.not.found");
		}
	}



	private void deleteToken(TokenEntity token) {
		em.remove(token);
	}

	@Override
	public TokenEntity checkAuthenticate(String email) throws DatabaseException {
		TypedQuery<TokenEntity> query2 = em.createNamedQuery(QueryNames.FIND_TOKEN_BY_EMAIL, TokenEntity.class);
		query2.setParameter("email", email);
		List<TokenEntity> tokens = query2.getResultList();
		if (tokens == null || tokens.size() == 0) {
			throw new DatabaseException("token not found", "token.not.found");
		}
		for(TokenEntity token : tokens) {
			if(!isTokienExpire(token)) {
				return token;
			};
		}
		throw new DatabaseException("token is expire", "token.is.expire");
	}



	private boolean isTokienExpire(TokenEntity token) {
		Date sysdate = new Date();
		if(sysdate.after(token.getExpireDate())) {
			deleteToken(token);
			addHistory("TOKEN_EXPIRE_BY_DATE", token);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public TokenEntity findToken(String token) throws DatabaseException {
		TypedQuery<TokenEntity> query = em.createNamedQuery(QueryNames.FIND_TOKEN_BY_TOKENID, TokenEntity.class);
		query.setParameter("token", token);
		List<TokenEntity> tokens = query.getResultList();
		if (tokens == null || tokens.size() == 0) {
			throw new DatabaseException("token not found", "token.not.found");
		}
		for(TokenEntity t : tokens) {
			if(!isTokienExpire(t)) {
				return t;
			};
		}
		throw new DatabaseException("token is expire", "token.is.expire");
	}

	@Override
	public void expireToken(String token) throws DatabaseException {
		TokenEntity t = findToken(token);
		deleteToken(t);
		addHistory("TOKEN_EXPIRE_BY_HYSTORY", t);
	}

	@Override
	public void updateExpireDate(String token) throws DatabaseException  {
		TokenEntity t = findToken(token);
		Date now = new Date();
		t.setExpireDate(ttl(now));
		addHistory("UPDATE_EXPIRE_DATE", t);
	}



	private Date ttl(Date now) {
		// TODO Auto-generated method stub
		return now;
	}

}
