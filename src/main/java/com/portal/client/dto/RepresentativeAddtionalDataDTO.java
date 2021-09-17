package com.portal.client.dto;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class RepresentativeAddtionalDataDTO {

	private String name, fantasyname, email, code;

	@JsonbCreator
	public RepresentativeAddtionalDataDTO(@JsonbProperty("name") String name,
			@JsonbProperty("fantasy_name") String fantasyname, @JsonbProperty("email") String email,
			@JsonbProperty("code") String code) {
		super();
		this.name = name;
		this.fantasyname = fantasyname;
		this.email = email;
		this.code = code;
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

}
