package com.portal.client.dto;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.portal.client.vo.CustomerOnOrder;

public class BudgetRequest extends BudgetRequestJsonSerializableFields {

	private CustomerOnOrder customerOnOrder;
	private BigDecimal grossValue;
	private BigDecimal liquidValue;
	private BigDecimal stValue;
	private BigDecimal globalDiscount;
	private final Set<ItemBudgetRequest> items;

	public BudgetRequest() {
		this.items = new HashSet<>();
		this.grossValue = BigDecimal.ZERO;
		this.liquidValue = BigDecimal.ZERO;
		this.stValue = BigDecimal.ZERO;
		this.globalDiscount = BigDecimal.ZERO;
		this.customerOnOrder = new CustomerOnOrder();
	}

	public CustomerOnOrder getCustomerOnOrder() {
		return customerOnOrder;
	}

	public void setCustomerOnOrder(CustomerOnOrder customer) {
		this.customerOnOrder = customer;
		super.setCustomerCode(customer.getCustomer().getCode());
		super.setCustomerStore(customer.getCustomer().getStore());
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

	public Set<ItemBudgetRequest> getItems() {
		return items;
	}

}
