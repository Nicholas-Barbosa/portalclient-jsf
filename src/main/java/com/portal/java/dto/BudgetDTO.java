package com.portal.java.dto;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.portal.java.dto.Product.ProductPrice;

public class BudgetDTO {

	private CustomerDTO customer;
	private BigDecimal grossValue;
	private BigDecimal liquidValue;
	private BigDecimal stValue;
	private BigDecimal globalDiscount;
	private final Set<Product> items;

	public BudgetDTO() {
		this.items = new HashSet<>();
		this.grossValue = BigDecimal.ZERO;
		this.liquidValue = BigDecimal.ZERO;
		this.stValue = BigDecimal.ZERO;
		this.globalDiscount = BigDecimal.ZERO;
	}

	public CustomerDTO getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerDTO customer) {
		this.customer = new CustomerDTO(customer);
	}

	public BigDecimal getGrossValue() {
		return grossValue;
	}

	public void setGrossValue(BigDecimal grossValue) {
		this.grossValue = grossValue;
	}

	public BigDecimal getLiquidValue() {
		return liquidValue;
	}

	public void setLiquidValue(BigDecimal liquidValue) {
		this.liquidValue = liquidValue;
	}

	public BigDecimal getStValue() {
		return stValue;
	}

	public void setStValue(BigDecimal stValue) {
		this.stValue = stValue;
	}

	public BigDecimal getGlobalDiscount() {
		return globalDiscount;
	}

	public void setGlobalDiscount(BigDecimal globalDiscount) {
		this.globalDiscount = globalDiscount;
	}

	public Set<Product> getItems() {
		return items;
	}

	public void addProduct(Product product) {
		ProductPrice prices = product.getPrice();
		grossValue = grossValue.add(prices.getTotalGrossValue());
		liquidValue = liquidValue.add(prices.getTotalStValue());
		stValue = stValue.add(prices.getTotalStValue());
		this.items.add(product);
	}
}
