package com.farawaybr.portal.security.user.builder;

import com.farawaybr.portal.security.user.ProtheusUser;
import com.farawaybr.portal.security.user.ProtheusUser.SaleType;

public class ProtheusUserBuilder {
	private String name, username, email, code, fantasyName;;
	private char[] password;
	private SaleType type;

	public static ProtheusUserBuilder getInstance() {
		return new ProtheusUserBuilder();
	}

	public ProtheusUserBuilder withName(String name) {
		this.name = name;
		return this;
	}

	public ProtheusUserBuilder withUsername(String name) {
		this.username = name;
		return this;
	}

	public ProtheusUserBuilder withEmail(String email) {
		this.email = email;
		return this;
	}

	public ProtheusUserBuilder withPassword(char[] password) {
		this.password = password;
		return this;
	}

	public ProtheusUserBuilder withCode(String code) {
		this.code = code;
		return this;
	}

	public ProtheusUserBuilder withFantasyName(String fantasyName) {
		this.fantasyName = fantasyName;
		return this;
	}

	public ProtheusUserBuilder withType(SaleType type) {
		this.type = type;
		return this;
	}

	public ProtheusUser build() {
		return new ProtheusUser(name, email, username, password, code, fantasyName, type);
	}
}
