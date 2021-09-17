package com.portal.client.dto;

import java.util.List;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class RepresentativeAddtionalDataWrapperDTO {

	private List<RepresentativeAddtionalDataDTO> data;

	@JsonbCreator
	public RepresentativeAddtionalDataWrapperDTO(
			@JsonbProperty("representative") List<RepresentativeAddtionalDataDTO> data) {
		super();
		this.data = data;
	}

	public RepresentativeAddtionalDataDTO getData() {
		return data.get(0);
	}

}