package com.portal.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SearchCustomerByCodeAndStoreDTO {

	@NotBlank
	@Size(min = 6, max = 6)
	private String code;

	@NotBlank
	private String store;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
	}

}
