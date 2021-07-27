package com.portal.client.dto;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class BrazilianState {

	private final String name;
	private final String acronym;

	public BrazilianState() {
		this("pr","pr");
	}
	@JsonbCreator
	public BrazilianState(@JsonbProperty("nome") String name,@JsonbProperty("sigla") String acronym) {
		super();
		this.name = name;
		this.acronym = acronym;
	}

	public String getName() {
		return name;
	}

	public String getAcronym() {
		return acronym;
	}

}