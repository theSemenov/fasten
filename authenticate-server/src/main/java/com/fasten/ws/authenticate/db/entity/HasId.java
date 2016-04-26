package com.fasten.ws.authenticate.db.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
		@NamedQuery(name = QueryNames.FIND_USER, query = "select u from UserEntity u where u.email = :email and u.password = :password"),
		@NamedQuery(name = QueryNames.FIND_TOKEN_BY_EMAIL, query = "select t from TokenEntity t where t.user.email = :email"),
		@NamedQuery(name = QueryNames.FIND_TOKEN_BY_TOKENID, query = "select t from TokenEntity t where t.token = :token")

})
public class HasId {
	@Id
	private long id;

	public HasId() {

	}
}
