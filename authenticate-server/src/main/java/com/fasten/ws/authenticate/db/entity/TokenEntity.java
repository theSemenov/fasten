package com.fasten.ws.authenticate.db.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
@Entity
@Table(name="user_token_tbl")
public class TokenEntity extends HasToken {

	public TokenEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
}
