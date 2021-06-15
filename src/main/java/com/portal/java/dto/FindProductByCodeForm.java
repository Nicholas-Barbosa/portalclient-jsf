package com.portal.java.dto;

import javax.validation.constraints.NotEmpty;

public class FindProductByCodeForm {

	@NotEmpty
	private String code;
	@NotEmpty
	private String customerCode;
	@NotEmpty
	private String customerStore;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public String getCustomerStore() {
		return customerStore;
	}

}
