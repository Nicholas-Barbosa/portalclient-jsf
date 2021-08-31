package com.portal.client.vo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.portal.client.dto.CustomerOnOrder;

public class Order {

	private String code,customerNumOrder,repNumOrder;
	private CustomerOnOrder customerOnOrder;
	private BigDecimal grossValue;
	private BigDecimal liquidValue;
	private BigDecimal stValue;
	private BigDecimal globalDiscount;
	private String message;
	private Set<Item> items;
	private LocalDate createdAt;

	public Order() {
		// TODO Auto-generated constructor stub
	}

	public Order(String code,String customerNumOrder,String repNumOrder, CustomerOnOrder customerOnOrder, BigDecimal grossValue, BigDecimal liquidValue,
			BigDecimal stValue, BigDecimal globalDiscount, String message, Set<Item> items, LocalDate createdAt) {
		super();
		this.code = code;
		this.customerOnOrder = customerOnOrder;
		this.grossValue = grossValue;
		this.liquidValue = liquidValue;
		this.stValue = stValue;
		this.globalDiscount = globalDiscount;
		this.message = message;

		this.items = items;
		this.createdAt = createdAt;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	
	public String getCustomerNumOrder() {
		return customerNumOrder;
	}

	public void setCustomerNumOrder(String customerNumOrder) {
		this.customerNumOrder = customerNumOrder;
	}

	public String getRepNumOrder() {
		return repNumOrder;
	}

	public void setRepNumOrder(String repNumOrder) {
		this.repNumOrder = repNumOrder;
	}

	public CustomerOnOrder getCustomerOnOrder() {
		return customerOnOrder;
	}

	public void setCustomerOnOrder(CustomerOnOrder customerOnOrder) {
		this.customerOnOrder = customerOnOrder;
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Set<Item> getItems() {
		return items;
	}

	public boolean addItem(Item item) {
		if (items == null) {
			items = new HashSet<>();
		}
		return this.items.add(item);
	}

	public boolean removeItem(Item item) {
		return this.items.remove(item);
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}
}
