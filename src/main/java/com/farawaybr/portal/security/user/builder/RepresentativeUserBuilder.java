package com.farawaybr.portal.security.user.builder;

import com.farawaybr.portal.security.user.RepresentativeUser;
import com.farawaybr.portal.security.user.RepresentativeUser.SaleType;

public class RepresentativeUserBuilder {
	private String name, username, email, code, fantasyName;;
	private char[] password;
	private SaleType type;

	public static RepresentativeUserBuilder getInstance() {
		return new RepresentativeUserBuilder();
	}

	public RepresentativeUserBuilder withName(String name) {
		this.name = name;
		return this;
	}

	public RepresentativeUserBuilder withUsername(String name) {
		this.username = name;
		return this;
	}

	public RepresentativeUserBuilder withEmail(String email) {
		this.email = email;
		return this;
	}

	public RepresentativeUserBuilder withPassword(char[] password) {
		this.password = password;
		return this;
	}

	public RepresentativeUserBuilder withCode(String code) {
		this.code = code;
		return this;
	}

	public RepresentativeUserBuilder withFantasyName(String fantasyName) {
		this.fantasyName = fantasyName;
		return this;
	}

	public RepresentativeUserBuilder withType(SaleType type) {
		this.type = type;
		return this;
	}

	public RepresentativeUser build() {
		return new RepresentativeUser(name, email, username, password, code, fantasyName, type);
	}
}
