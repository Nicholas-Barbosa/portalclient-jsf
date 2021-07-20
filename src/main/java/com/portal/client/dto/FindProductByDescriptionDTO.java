package com.portal.client.dto;

import javax.validation.constraints.NotBlank;

public class FindProductByDescriptionDTO {

	@NotBlank
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
