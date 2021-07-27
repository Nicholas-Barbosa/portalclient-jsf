package com.portal.client.dto;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class BaseBudget {

	private String idCode;
	private CustomerOnOrder customerOnOrder;
	private BigDecimal grossValue;
	private BigDecimal liquidValue;
	private BigDecimal stValue;
	private BigDecimal globalDiscount;
	private final Set<ItemBudget> items;

	public BaseBudget() {
		this.items = new HashSet<>();
		this.grossValue = BigDecimal.ZERO;
		this.liquidValue = BigDecimal.ZERO;
		this.stValue = BigDecimal.ZERO;
		this.globalDiscount = BigDecimal.ZERO;
	}

	public BaseBudget(BaseBudget budget) {
		this.idCode = budget.idCode;
		this.customerOnOrder = budget.customerOnOrder;
		this.grossValue = budget.grossValue;
		this.liquidValue = budget.liquidValue;
		this.stValue = budget.stValue;
		this.globalDiscount = budget.globalDiscount;
		this.items = new HashSet<>(budget.getItems());
	}

	public BaseBudget(CustomerOnOrder customerOnOrder, BigDecimal grossValue, BigDecimal liquidValue,
			BigDecimal stValue, BigDecimal globalDiscount, Set<ItemBudget> items) {
		super();
		this.customerOnOrder = customerOnOrder;
		this.grossValue = grossValue;
		this.liquidValue = liquidValue;
		this.stValue = stValue;
		this.globalDiscount = globalDiscount;
		this.items = items;
	}

	public String getIdCode() {
		return idCode;
	}

	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}

	public CustomerOnOrder getCustomerOnOrder() {
		return customerOnOrder;
	}

	public void setCustomerOnOrder(CustomerOnOrder customer) {
		this.customerOnOrder = customer;

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

	public Set<? extends ItemBudget> getItems() {
		return new HashSet<>(items);
	}

	public void addItem(ItemBudget item) {
		this.items.add(item);
	}

	public boolean removeItem(ItemBudget item) {
		return this.items.remove(item);
	}

}
