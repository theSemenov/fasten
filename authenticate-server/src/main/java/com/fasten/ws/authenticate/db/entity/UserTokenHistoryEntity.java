package com.fasten.ws.authenticate.db.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="user_token_history_tbl")
public class UserTokenHistoryEntity extends HasToken {
	private String action;
	
	public UserTokenHistoryEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public UserTokenHistoryEntity(String action, TokenEntity token) {
		super();
		this.action = action;
		this.setExpireDate(token.getExpireDate());
		this.setLoginDate(token.getLoginDate());
		this.setToken(token.getToken());
		this.setUser(token.getUser());
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
}
