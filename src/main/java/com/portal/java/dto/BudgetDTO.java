package com.portal.java.dto;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.portal.java.dto.ProductDTO.ProductPriceDTO;

public class BudgetDTO {

	private CustomerDTO customer;
	private BigDecimal grossValue;
	private BigDecimal liquidValue;
	private final Set<ProductDTO> items;

	public BudgetDTO() {
		this.items = new HashSet<>();
		this.grossValue = BigDecimal.ZERO;
		this.liquidValue = BigDecimal.ZERO;
	}

	public BudgetDTO(CustomerDTO customer, BigDecimal grossValue, BigDecimal liquidValue, Set<ProductDTO> items) {
		super();
		this.customer = new CustomerDTO(customer);
		this.grossValue = grossValue;
		this.liquidValue = liquidValue;
		this.items = new HashSet<>(items);
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

	public Set<ProductDTO> getItems() {
		return items;
	}

	public void addProduct(ProductDTO product) {
		ProductPriceDTO prices = product.getPrice();
		grossValue = grossValue.add(prices.getTotalGrossValue());
		liquidValue = liquidValue.add(prices.getTotalStValue());
		this.items.add(product);
	}
}
