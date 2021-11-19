package com.portal.client.vo;

import java.math.BigDecimal;

public class ProductDiscountData extends BaseProductPriceState {

	private float discount, budgetGlobalDiscount, totalDiscount;

	public ProductDiscountData() {
		// TODO Auto-generated constructor stub
	}

	public ProductDiscountData(BigDecimal unitValue, BigDecimal unitStValue, BigDecimal unitGrossValue,
			BigDecimal totalValue, BigDecimal totalStValue, BigDecimal totalGrossValue, float discount,
			float budgetGlobalDiscount) {
		super(unitStValue, unitValue, unitGrossValue, totalStValue, totalValue, totalGrossValue);
		this.discount = discount;
		this.budgetGlobalDiscount = budgetGlobalDiscount;
	}

	public float getDiscount() {
		return discount;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
	}

	public float getBudgetGlobalDiscount() {
		return budgetGlobalDiscount;
	}

	public void setBudgetGlobalDiscount(float budgetGlobalDiscount) {
		this.budgetGlobalDiscount = budgetGlobalDiscount;
	}

	public float getTotalDiscount() {
		return totalDiscount;
	}

}
