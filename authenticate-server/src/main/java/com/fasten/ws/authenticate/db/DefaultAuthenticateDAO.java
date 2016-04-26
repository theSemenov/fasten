package com.fasten.ws.authenticate.db;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import javax.xml.registry.infomodel.User;

import org.apache.commons.codec.binary.Base64;
import org.joda.time.DateTime;

import com.fasten.ws.authenticate.db.entity.QueryNames;
import com.fasten.ws.authenticate.db.entity.TokenEntity;
import com.fasten.ws.authenticate.db.entity.UserEntity;
import com.fasten.ws.authenticate.db.entity.UserTokenHistoryEntity;
import com.fasten.ws.authenticate.exceptions.DatabaseException;
import com.fasten.ws.authenticate.model.LoginModel;
import com.fasten.ws.authenticate.model.TokenModel;

@Stateless
public class DefaultAuthenticateDAO implements AuthenticateDAO {
	@PersistenceContext(unitName="FastenDS")
	private EntityManager em;
	private Integer ttlMin = 10;
	
	@Override
	public TokenModel authenticate(String email, String password) throws DatabaseException  {
		UserEntity user = findUser(email, password);
		TokenEntity token = null;
		try {
			token = checkAuthenticate(email);
		} catch (Exception e) {
		}
		if(token != null) {
			deleteToken(token);
			addHistory("RELOGIN_REQUEST", token);
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
		String token = UUID.randomUUID().toString();
		newToken.setToken(token);
		newToken.setExpireDate(setTtl(loginDate));
		newToken.setLoginDate(loginDate);
		em.persist(newToken);
		return newToken;
	}



	private UserEntity findUser(String email, String password) throws DatabaseException {
		TypedQuery<UserEntity> query = em.createNamedQuery(QueryNames.FIND_USER, UserEntity.class);
		query.setParameter("email", email);
		query.setParameter("password", password);
		try {
			List<UserEntity> users = query.getResultList();
			if(users.size() == 0)
				throw new NullPointerException();
			return users.get(0);
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
		addHistory("TOKEN_EXPIRE", t);
	}

	@Override
	public void updateExpireDate(String token) throws DatabaseException  {
		TokenEntity t = findToken(token);
		Date now = new Date();
		t.setExpireDate(setTtl(now));
		addHistory("UPDATE_EXPIRE_DATE", t);
	}



	private Date setTtl(Date now) {
		DateTime d = new DateTime(now);
		DateTime expire = d.plusMinutes(ttlMin);
		return expire.toDate();
	}



	@Override
	public LoginModel register(String email, String password)
			throws DatabaseException {
		UserEntity checkUser = null;
		try {
			checkUser = findUser(email, password);
		} catch (DatabaseException e) {
			
		}
		if (checkUser != null) {
			throw new DatabaseException("Can not register. User alredy exists", "user.alredy.exists");
		}
		UserEntity newUser = new UserEntity();
		newUser.setEmail(email);
		newUser.setPassword(password);
		em.persist(newUser);
		return newUser.toLoginModel();
	}



	@Override
	public boolean delete(String email, String password)
			throws DatabaseException {
		UserEntity checkUser = null;
		try {
			checkUser = findUser(email, password);
		} catch (DatabaseException e) {
			
		}
		if (checkUser == null) {
			throw new DatabaseException("Can not delete. User not exists", "user.not.exists");
		}
		TypedQuery<TokenEntity> queryToken = em.createNamedQuery(QueryNames.FIND_TOKEN_BY_EMAIL, TokenEntity.class);
		queryToken.setParameter("email", checkUser.getEmail());
		for(TokenEntity token : queryToken.getResultList()) {
			em.remove(token);
		}
		TypedQuery<UserTokenHistoryEntity> queryHistory = em.createNamedQuery(QueryNames.FIND_TOKEN_HISTORY_BY_EMAIL, UserTokenHistoryEntity.class);
		queryHistory.setParameter("email", checkUser.getEmail());
		for(UserTokenHistoryEntity token : queryHistory.getResultList()) {
			em.remove(token);
		}
		em.remove(checkUser);
		return true;
	}

}
