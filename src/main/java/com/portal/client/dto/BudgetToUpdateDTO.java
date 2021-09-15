package com.portal.client.dto;

import java.math.BigDecimal;

import javax.json.bind.annotation.JsonbProperty;

import com.portal.client.vo.Budget;

public class BudgetToUpdateDTO extends BudgetToSaveJsonSerializable {

	public BudgetToUpdateDTO(Budget budget) {
		super(budget);
	}

	@JsonbProperty("budget")
	public String getCode() {
		return super.getBudget().getCode();
	}

	@JsonbProperty("liquid_order_value")
	public BigDecimal getLiquidValue() {
		return super.getBudget().getLiquidValue();
	}

	@JsonbProperty("gross_order_value")
	public BigDecimal getGrossValue() {
		return super.getBudget().getGrossValue();
	}

	@JsonbProperty("gross_order_value")
	public String getCustomerDescription() {
		return super.getBudget().getCustomerOnOrder().getName();
	}
}
