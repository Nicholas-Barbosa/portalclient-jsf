package com.farawaybr.portal.dto;

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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((acronym == null) ? 0 : acronym.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BrazilianState other = (BrazilianState) obj;
		if (acronym == null) {
			if (other.acronym != null)
				return false;
		} else if (!acronym.equalsIgnoreCase(other.acronym))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equalsIgnoreCase(other.name))
			return false;
		return true;
	}

	
}