package com.fasten.ws.authenticate.db.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
@Entity
@Table(name="user_tbl")
public class UserEntity extends HasId {

	private String email;
	private String password;
	public UserEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
