package com.portal.client.security.user.builder;

import com.portal.client.security.user.User;

public class UserBuilder {

	private String name, username, email;
	private char[] password;

	public static UserBuilder getInstance() {
		return new UserBuilder();
	}

	public UserBuilder withName(String name) {
		this.name = name;
		return this;
	}

	public UserBuilder withUsername(String name) {
		this.username = name;
		return this;
	}

	public UserBuilder withEmail(String email) {
		this.email = email;
		return this;
	}

	public UserBuilder withPassword(char[] password) {
		this.password = password;
		return this;
	}

	public User build() {
		return new User(name, username, email, password);
	}

	protected String getName() {
		return name;
	}

	protected String getUsername() {
		return username;
	}

	protected String getEmail() {
		return email;
	}

	protected char[] getPassword() {
		return password;
	}
	
	
}
