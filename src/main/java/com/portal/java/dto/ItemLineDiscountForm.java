package com.portal.java.dto;

import java.math.BigDecimal;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class ItemLineDiscountForm {

	@NotEmpty
	private String line;
	@Max(value = 100)
	@Min(0)
	private BigDecimal discount;

	public ItemLineDiscountForm() {
		// TODO Auto-generated constructor stub
	}
	
	public ItemLineDiscountForm(@NotEmpty String line, @Max(100) @Min(0) BigDecimal discount) {
		super();
		this.line = line;
		this.discount = discount;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

}
