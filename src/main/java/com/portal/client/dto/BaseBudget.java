package com.portal.client.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class BaseBudget {

	private String idCode;
	private CustomerOnOrder customerOnOrder;
	private BigDecimal grossValue;
	private BigDecimal liquidValue;
	private BigDecimal stValue;
	private BigDecimal globalDiscount;
	private String message, customerOrder, representativeOrder;
	private final Set<ItemBudget> items;
	private LocalDate createdAt;

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
		this.message = budget.message;
		this.representativeOrder = budget.representativeOrder;
		this.customerOrder = budget.customerOrder;
		this.createdAt = budget.createdAt;
	}

	public BaseBudget(CustomerOnOrder customerOnOrder, BigDecimal grossValue, BigDecimal liquidValue,
			BigDecimal stValue, BigDecimal globalDiscount, Set<ItemBudget> items, String message) {
		super();
		this.customerOnOrder = customerOnOrder;
		this.grossValue = grossValue;
		this.liquidValue = liquidValue;
		this.stValue = stValue;
		this.globalDiscount = globalDiscount;
		this.items = items;
		this.message = message;
	}

	public BaseBudget(String idCode, CustomerOnOrder customerOnOrder, BigDecimal grossValue, BigDecimal liquidValue,
			BigDecimal stValue, BigDecimal globalDiscount, Set<? extends ItemBudget> items, String message) {
		super();
		this.idCode = idCode;
		this.customerOnOrder = customerOnOrder;
		this.grossValue = grossValue;
		this.liquidValue = liquidValue;
		this.stValue = stValue;
		this.globalDiscount = globalDiscount;
		this.items = items == null ? null : new HashSet<>(items);
		this.message = message;
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

	public void replaceItems(Collection<? extends ItemBudget> newItems) {
		this.items.clear();
		this.items.addAll(newItems);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRepresentativeOrder() {
		return representativeOrder;
	}

	public void setRepresentativeOrder(String representativeOrder) {
		this.representativeOrder = representativeOrder;
	}

	public String getCustomerOrder() {
		return customerOrder;
	}

	public void setCustomerOrder(String customerOrder) {
		this.customerOrder = customerOrder;
	}

	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}
}
