package com.farawaybr.portal.dto;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class WrapperRepresentativeData {

	private UserData[] representativeData;

	@JsonbCreator
	public WrapperRepresentativeData(@JsonbProperty("representative") UserData[] representativeData) {
		this.representativeData = representativeData;
	}

	public UserData[] getRepresentativeData() {
		return representativeData;
	}

	public UserData getData() {
		return representativeData[0];
	}
}
