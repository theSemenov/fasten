package com.fasten.ws.authenticate.db.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
@Entity
@Table(name="token_tbl")
public class TokenEntity extends HasId{
	private UserEntity user;
	private String token;
	private Date loginDate;
	private Date expireDate;
}
