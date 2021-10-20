package com.portal.client.dto;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

import com.portal.client.security.user.RepresentativeUser.RepresentativeType;

public class RepresentativeData {

	private String name, fantasyname, email, code;
	private RepresentativeType type;

	@JsonbCreator
	public RepresentativeData(@JsonbProperty(value = "name") String name, @JsonbProperty("email") String email,
			@JsonbProperty("code") String code, @JsonbProperty("type") String type) {
		super();
		this.name = name;
		this.email = email;
		this.code = code;
		this.type = RepresentativeType.fromAcronym(type);
	}

	public String getName() {
		return name;
	}

	public String getFantasyname() {
		return fantasyname;
	}

	public String getEmail() {
		return email;
	}

	public String getCode() {
		return code;

	}

	public RepresentativeType getType() {
		return type;
	}

	@JsonbProperty("fantasy_name")
	public void setFantasyname(String fantasyname) {
		this.fantasyname = fantasyname;
	}
}