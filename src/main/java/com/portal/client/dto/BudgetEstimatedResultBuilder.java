package com.portal.client.dto;

import java.math.BigDecimal;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

import com.portal.client.dto.builder.ItemBudgetEstimatedResultBuilder;

public class BudgetEstimatedResultBuilder extends BaseBudgetJsonBuilder {

	@JsonbCreator
	public BudgetEstimatedResultBuilder(@JsonbProperty("liquid_order_value") BigDecimal liquidValue,
			@JsonbProperty("gross_order_value") BigDecimal grossValue,
			@JsonbProperty("client_code") String customerCode,
			@JsonbProperty("estimate") Set<ItemBudgetEstimatedResultBuilder> items) {
		super.withCustomerCode(customerCode).withGrossValue(grossValue).withLiquidValue(liquidValue).withItems(items);
	}

	@Override
	public BaseBudget build() {
		CustomerOnOrder customerOnOrder = new CustomerOnOrder(super.getCustomerCode(), super.getCustomerStore(), null,
				null, null, null, null, null, null);
		Set<ItemBudget> items = super.getItems().parallelStream().map(i -> i.build()).collect(CopyOnWriteArraySet::new,
				Set::add, Set::addAll);
		BaseBudget baseBudget = new BaseBudget(customerOnOrder, super.getGrossValue(), super.getLiquidValue(),
				super.getStValue(), BigDecimal.ZERO, items, super.getMessage());
		return baseBudget;
	}
}
