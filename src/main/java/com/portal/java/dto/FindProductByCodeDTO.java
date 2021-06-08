package com.portal.java.dto;

import javax.validation.constraints.NotEmpty;

public class FindProductByCodeDTO {

	@NotEmpty
	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
