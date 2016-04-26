package com.fasten.ws.authenticate.db.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@MappedSuperclass
@NamedQueries({
		@NamedQuery(name = QueryNames.FIND_USER, query = "select u from UserEntity u where u.email = :email and u.password = :password"),
		@NamedQuery(name = QueryNames.FIND_TOKEN_BY_EMAIL, query = "select t from TokenEntity t where t.user.email = :email"),
		@NamedQuery(name = QueryNames.FIND_TOKEN_BY_TOKENID, query = "select t from TokenEntity t where t.token = :token"),
		@NamedQuery(name = QueryNames.FIND_TOKEN_HISTORY_BY_EMAIL, query = "select t from UserTokenHistoryEntity t where t.user.email = :email")
})
public class HasId {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	public HasId() {

	}
}
