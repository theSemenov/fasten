package com.fasten.ws.authenticate.db;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.fasten.ws.authenticate.db.entity.QueryNames;
import com.fasten.ws.authenticate.model.LoginModel;

@Stateless
public class DefaultAuthenticateDAO implements AuthenticateDAO {

	EntityManager em;
	
	@Override
	public LoginModel authenticate(String email, String password) {
		TypedQuery<Integer> query = em.createNamedQuery(QueryNames.FIND_USER, Integer.class);
		query.setParameter("email", email);
		query.setParameter("password", password);
		Integer hasUser = query.getResultList().get(0);
		return null;
	}

	@Override
	public LoginModel checkAuthenticate(String email, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoginModel checkToken(String token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoginModel expireToken(String token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoginModel updateExpireDate(String token) {
		// TODO Auto-generated method stub
		return null;
	}

}
