package com.portal.client.vo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.portal.client.dto.CustomerOnOrder;

public class Budget extends Order {

	public Budget() {

	}

	public Budget(String customerNumOrder, String repNumOrder, CustomerOnOrder customerOnOrder, BigDecimal grossValue,
			BigDecimal liquidValue, BigDecimal stValue, BigDecimal globalDiscount, List<Item> items, String message,
			LocalDate createdAt) {
		super(null, customerNumOrder, repNumOrder, customerOnOrder, grossValue, liquidValue, stValue, globalDiscount,
				message, items, createdAt);

	}

	public Budget(String code, String customerNumOrder, String repNumOrder, CustomerOnOrder customerOnOrder,
			BigDecimal grossValue, BigDecimal liquidValue, BigDecimal stValue, BigDecimal globalDiscount,
			List<Item> items, String message, LocalDate createdAt) {
		super(code, customerNumOrder, repNumOrder, customerOnOrder, grossValue, liquidValue, stValue, globalDiscount,
				message, items, createdAt);

	}

	

}
