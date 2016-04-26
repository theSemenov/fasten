package com.fasten.ws.authenticate.db.entity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.fasten.ws.authenticate.model.LoginModel;
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
	
	public LoginModel toLoginModel() {
		LoginModel loginModel = new LoginModel();
		loginModel.setEamil(email);
		loginModel.setPassword(password);
		return loginModel;
		
	}
}
