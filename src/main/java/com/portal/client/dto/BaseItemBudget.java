package com.portal.client.dto;

import java.math.BigDecimal;

import com.portal.client.vo.Product;

public class BaseItemBudget {

	private final Product product;
	private ItemBudgetValue value;

	public BaseItemBudget(BigDecimal budgetGlobalDiscount, BigDecimal lineDiscount, Product product,
			ItemBudgetValue value) {
		super();
		this.product = product;
		this.value = value;
	}

	public BaseItemBudget() {
		this(BigDecimal.ZERO, BigDecimal.ZERO, null, null);
	}

	public Product getProduct() {
		return product;
	}

	public ItemBudgetValue getValues() {
		return value;
	}

	public String line() {
		return product.getLine();
	}

}
