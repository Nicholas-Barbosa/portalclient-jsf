package com.portal.client.dto;

import java.math.BigDecimal;
import java.util.List;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

import com.portal.client.vo.Budget;
import com.portal.client.vo.builder.ItemBudgetEstimatedResultBuilder;

public class BudgetEstimatedResultBuilder extends BaseBudgetJsonBuilder {

	@JsonbCreator
	public BudgetEstimatedResultBuilder(@JsonbProperty("liquid_order_value") BigDecimal liquidValue,
			@JsonbProperty("gross_order_value") BigDecimal grossValue,
			@JsonbProperty("client_code") String customerCode,
			@JsonbProperty("estimate") List<ItemBudgetEstimatedResultBuilder> items) {
		super.withCustomerCode(customerCode).withGrossValue(grossValue).withLiquidValue(liquidValue)
				.withItems(ItemBudgetEstimatedResultBuilder.build(items));
	}

	@Override
	public Budget build() {
		CustomerOnOrder customerOnOrder = new CustomerOnOrder(super.getCustomerCode(), super.getCustomerStore(), null,
				null, null, null, null, null, null);
		Budget baseBudget = new Budget(null, null, customerOnOrder, super.getGrossValue(), super.getLiquidValue(),
				super.getStValue(), BigDecimal.ZERO, super.getItems(), super.getMessage(), null);
		return baseBudget;
	}

}
