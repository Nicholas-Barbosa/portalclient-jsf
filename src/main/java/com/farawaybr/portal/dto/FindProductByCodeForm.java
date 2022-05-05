package com.farawaybr.portal.dto;

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
		this.code = code.strip();
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public String getCustomerStore() {
		return customerStore;
	}

	public void setCustomerCode(String customerCode2) {
		this.customerCode = customerCode2;
	}

	public void setCustomerStore(String customerStore) {
		this.customerStore = customerStore;
	}

}
