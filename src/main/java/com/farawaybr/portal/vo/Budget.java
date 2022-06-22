package com.farawaybr.portal.vo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.farawaybr.portal.security.user.ProtheusUser;

public class Budget extends Order {

	public Budget() {

	}


	public Budget(String code, String customerNumOrder, String repNumOrder, CustomerOnOrder customerOnOrder,
			BigDecimal grossValue, BigDecimal liquidValue, BigDecimal stValue, BigDecimal globalDiscount,
			List<Item> items, String message, LocalDate createdAt, ProtheusUser representative) {
		super(code,customerNumOrder, repNumOrder, customerOnOrder, grossValue, liquidValue, stValue, globalDiscount, message, items,
				createdAt, representative, null);

	}

	
}
