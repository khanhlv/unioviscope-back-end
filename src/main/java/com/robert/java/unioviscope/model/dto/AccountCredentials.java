package com.robert.java.unioviscope.model.dto;

import java.io.Serializable;

public class AccountCredentials implements Serializable {

	private static final long serialVersionUID = -3204502995106211166L;
	
	private String userName;
	private String password;

	AccountCredentials() {

	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}