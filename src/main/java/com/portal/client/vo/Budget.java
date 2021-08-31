package com.portal.client.vo;

import java.math.BigDecimal;
import java.util.Set;

import com.portal.client.dto.CustomerOnOrder;

public class Budget extends Order {

	public Budget() {

	}

	public Budget(CustomerOnOrder customerOnOrder, BigDecimal grossValue, BigDecimal liquidValue, BigDecimal stValue,
			BigDecimal globalDiscount, Set<Item> items, String message) {
		super(null, null, null, customerOnOrder, grossValue, liquidValue, stValue, globalDiscount, message, items,
				null);

	}

	public Budget(String code, CustomerOnOrder customerOnOrder, BigDecimal grossValue, BigDecimal liquidValue,
			BigDecimal stValue, BigDecimal globalDiscount, Set<Item> items, String message) {
		super(code, null, null, customerOnOrder, grossValue, liquidValue, stValue, globalDiscount, message, items,
				null);

	}

}
