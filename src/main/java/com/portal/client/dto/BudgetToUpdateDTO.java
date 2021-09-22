package com.portal.client.dto;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

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

	@JsonbProperty("client_description")
	public String getCustomerDescription() {
		return super.getBudget().getCustomerOnOrder().getName();
	}

	@Override
	@JsonbProperty("representative_order")
	public String getRepresentativeOrder() {
		// TODO Auto-generated method stub
		String value = super.getRepresentativeOrder();
		return value == null ? "empty" : value;
	}

	@Override
	@JsonbProperty("client_order")
	public String getCustomerOrder() {
		// TODO Auto-generated method stub
		String value = super.getCustomerOrder();
		return value == null ? "empty" : value;
	}

	@Override
	@JsonbProperty("items")
	public Set<ItemBudgetToSaveJsonSerializable> getItems() {
		return super.getBudget().getItems().stream().map(ItemBudgetToUpdateJsonSerializable::of).collect(Collectors.toSet());
	}
}
