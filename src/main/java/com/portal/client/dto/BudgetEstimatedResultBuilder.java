package com.portal.client.dto;

import java.math.BigDecimal;
import java.util.Set;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class BudgetEstimatedResultBuilder extends BaseBudgetJsonBuilder {

	@JsonbCreator
	public BaseBudgetJsonBuilder ofJsonb(@JsonbProperty("liquid_order_value") BigDecimal liquidValue,
			@JsonbProperty("gross_order_value") BigDecimal grossValue,
			@JsonbProperty("client_code") String customerCode,
			@JsonbProperty("estimate") Set<ItemBudgetEstimatedResultBuilder> items) {
		return super.withCustomerCode(customerCode).withGrossValue(grossValue).withLiquidValue(liquidValue)
				.withItems(items);
	}

	@Override
	public BaseBudget build() {
		// TODO Auto-generated method stub
		return null;
	}
}
