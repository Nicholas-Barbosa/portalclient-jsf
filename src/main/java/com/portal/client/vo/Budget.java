package com.portal.client.vo;

import java.math.BigDecimal;
import java.util.Set;

import com.portal.client.dto.CustomerOnOrder;

public class Budget extends Order {

	private String customerOrder, representativeOrder;

	public Budget() {

	}

	public Budget(CustomerOnOrder customerOnOrder, BigDecimal grossValue, BigDecimal liquidValue, BigDecimal stValue,
			BigDecimal globalDiscount, Set<Item> items, String message) {
		super(null, customerOnOrder, grossValue, liquidValue, stValue, globalDiscount, message, items, null);

	}

	public Budget(String code, CustomerOnOrder customerOnOrder, BigDecimal grossValue, BigDecimal liquidValue,
			BigDecimal stValue, BigDecimal globalDiscount, Set<Item> items, String message) {
		super(code, customerOnOrder, grossValue, liquidValue, stValue, globalDiscount, message, items, null);

	}

	public String getCustomerOrder() {
		return customerOrder;
	}

	public void setCustomerOrder(String customerOrder) {
		this.customerOrder = customerOrder;
	}

	public String getRepresentativeOrder() {
		return representativeOrder;
	}

	public void setRepresentativeOrder(String representativeOrder) {
		this.representativeOrder = representativeOrder;
	}

}
