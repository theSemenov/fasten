package com.fasten.ws.authenticate.db.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
@Entity
@Table(name="user_tbl")
public class UserEntity extends HasId {

	private String email;
	private String password;
}
