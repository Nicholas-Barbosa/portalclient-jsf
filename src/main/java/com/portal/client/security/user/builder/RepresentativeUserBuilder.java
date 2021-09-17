package com.portal.client.security.user.builder;

import com.portal.client.security.user.RepresentativeUser;

public class RepresentativeUserBuilder extends UserBuilder {

	private String code, fantasyName;

	public static RepresentativeUserBuilder getInstance() {
		return new RepresentativeUserBuilder();
	}

	public RepresentativeUserBuilder withCode(String code) {
		this.code = code;
		return this;
	}

	public RepresentativeUserBuilder withFantasyName(String fantasyName) {
		this.fantasyName = fantasyName;
		return this;
	}

	@Override
	public RepresentativeUser build() {
		// TODO Auto-generated method stub
		return new RepresentativeUser(code, fantasyName, super.getName(), super.getUsername(), super.getEmail(),
				super.getPassword());
	}
}
