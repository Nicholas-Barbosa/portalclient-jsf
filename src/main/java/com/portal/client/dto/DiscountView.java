package com.portal.client.dto;

import java.math.BigDecimal;

import com.portal.client.vo.ItemBudget;

public class DiscountView {

	private BigDecimal lineDiscount;
	private BigDecimal globalDiscount;

	public DiscountView() {
		// TODO Auto-generated constructor stub
	}

	public BigDecimal getLineDiscount() {
		return lineDiscount;
	}

	public void setLineDiscount(BigDecimal lineDiscount) {
		this.lineDiscount = lineDiscount;
	}

	public BigDecimal getGlobalDiscount() {
		return globalDiscount;
	}

	public void setGlobalDiscount(BigDecimal globalDiscount) {
		this.globalDiscount = globalDiscount;
	}

}
