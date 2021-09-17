package com.portal.client.security.user;

import java.io.Serializable;
import java.util.Arrays;

public class User implements DataValidator, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5781831668572790295L;
	private String name, email;
	private final String username;
	private final char[] password;

	public User(String name, String username, String email, char[] password) {
		super();
		this.name = name;
		this.username = username;
		this.email = email;
		this.password = password.clone();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public char[] getPassword() {
		return password.clone();
	}

	@Override
	public boolean isDataComplete() {
		// TODO Auto-generated method stub
		return name != null && email != null && username != null && password != null;
	}

	public Object writeReplace() {
		Arrays.fill(password, '*');
		return this;
	}
}
