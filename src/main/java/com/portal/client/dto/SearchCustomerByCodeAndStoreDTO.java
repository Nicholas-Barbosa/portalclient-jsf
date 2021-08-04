package com.portal.client.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SearchCustomerByCodeAndStoreDTO {

	@NotBlank
	@Size(min = 6, max = 6)
	private String code;

	@NotBlank
	private String store;

	public SearchCustomerByCodeAndStoreDTO() {
		// TODO Auto-generated constructor stub
	}

	public SearchCustomerByCodeAndStoreDTO(String code2, String store2) {
		this.code = code2;
		this.store = store2;
	}

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
