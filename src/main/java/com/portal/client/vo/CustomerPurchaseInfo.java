package com.portal.client.vo;

import java.time.LocalDateTime;

public class CustomerPurchaseInfo {

	private final float discount;
	private final float discount2;
	private final float discount3;
	private final LocalDateTime lastPurchase;
	private final Character risk;
	private final String paymentTerms;
	private final String table;
	private final Double limit;

	public CustomerPurchaseInfo(float discount, float discount2, float discount3, LocalDateTime lastPurchase, Character risk,
			String paymentTerms, String table, Double limit) {
		super();
		this.discount = discount;
		this.discount2 = discount2;
		this.discount3 = discount3;
		this.lastPurchase = lastPurchase;
		this.risk = risk;
		this.paymentTerms = paymentTerms;
		this.table = table;
		this.limit = limit;
	}

	public float getDiscount() {
		return discount;
	}

	public float getDiscount2() {
		return discount2;
	}

	public float getDiscount3() {
		return discount3;
	}

	public LocalDateTime getLastPurchase() {
		return lastPurchase;
	}

	public Character getRisk() {
		return risk;
	}

	public String getPaymentTerms() {
		return paymentTerms;
	}

	public String getTable() {
		return table;
	}

	public Double getLimit() {
		return limit;
	}
}
