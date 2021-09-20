package com.portal.client.dto;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class WrapperRepresentativeData {

	private RepresentativeData[] representativeData;

	@JsonbCreator
	public WrapperRepresentativeData(@JsonbProperty("representative") RepresentativeData[] representativeData) {
		this.representativeData = representativeData;
	}

	public RepresentativeData[] getRepresentativeData() {
		return representativeData;
	}

	public RepresentativeData getData() {
		return representativeData[0];
	}
}
