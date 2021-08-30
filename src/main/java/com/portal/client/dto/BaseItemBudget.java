package com.portal.client.dto;

import java.math.BigDecimal;

import com.portal.client.vo.ItemValue;
import com.portal.client.vo.Product;

public class BaseItemBudget {

	private final Product product;
	private ItemValue value;

	public BaseItemBudget(BigDecimal budgetGlobalDiscount, BigDecimal lineDiscount, Product product,
			ItemValue value) {
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

	public ItemValue getValues() {
		return value;
	}

	public String line() {
		return product.getLine();
	}

}
