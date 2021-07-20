package com.portal.client.dto;

import java.time.LocalDate;

import javax.json.bind.annotation.JsonbProperty;

public class UserDTO {

	@JsonbProperty
	private String name;
	@JsonbProperty
	private LocalDate createdAt;

	public UserDTO(String name, LocalDate createdAt) {
		super();
		this.name = name;
		this.createdAt = createdAt;
	}

	public String getName() {
		return name;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

}
