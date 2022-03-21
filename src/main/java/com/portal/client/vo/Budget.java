package com.portal.client.vo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.portal.client.security.user.RepresentativeUser;

public class Budget extends Order {

	public Budget() {

	}

	private String customerNumOrder;

	public Budget(String code, String customerNumOrder, String repNumOrder, CustomerOnOrder customerOnOrder,
			BigDecimal grossValue, BigDecimal liquidValue, BigDecimal stValue, BigDecimal globalDiscount,
			List<Item> items, String message, LocalDate createdAt, RepresentativeUser representative) {
		super(code, repNumOrder, customerOnOrder, grossValue, liquidValue, stValue, globalDiscount, message, items,
				createdAt, representative, null);
		this.customerNumOrder = customerNumOrder;

	}

	public String getCustomerOrder() {
		return customerNumOrder;
	}

	public void setCustomerOrder(String customerNumOrder) {
		this.customerNumOrder = customerNumOrder;
	}
}
