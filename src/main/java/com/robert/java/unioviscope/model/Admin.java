package com.robert.java.unioviscope.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends User {

	private static final long serialVersionUID = -3901544609521291065L;

	Admin() {

	}

	public Admin(String userName) {
		super(userName);
	}

	@Override
	public String toString() {
		return "Admin [userName=" + getUserName() + ", role=" + getRole() + "]";
	}
}
