package com.farawaybr.portal.osiip;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class IpCountry {

	private String name, fullName, flagEmoji;
	private int callingCode;

	@JsonbCreator
	public IpCountry(@JsonbProperty("isoName") String name, @JsonbProperty("isoNameFull") String fullName,
			@JsonbProperty("callingCode") int callingCode, @JsonbProperty("countryFlagEmoji") String flagEmoji) {
		super();
		this.name = name;
		this.fullName = fullName;
		this.callingCode = callingCode;
		this.flagEmoji = flagEmoji;
	}

	public String getName() {
		return name;
	}

	public String getFullName() {
		return fullName;
	}

	public int getCallingCode() {
		return callingCode;
	}

	public String getFlagEmoji() {
		return flagEmoji;
	}
}
