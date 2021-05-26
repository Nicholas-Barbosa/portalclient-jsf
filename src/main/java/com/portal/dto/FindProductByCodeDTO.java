package com.portal.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class FindProductByCodeDTO {

	@NotBlank
	private String code;
	@Min(1)
	private int quantity;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
