package com.farawaybr.portal.dto;

import java.math.BigDecimal;

import javax.json.bind.annotation.JsonbProperty;

import com.farawaybr.portal.vo.Item;

public class ItemBudgetToUpdateJsonSerializable extends ItemBudgetToSaveJsonSerializable {

	public static ItemBudgetToUpdateJsonSerializable of(Item item) {
		return new ItemBudgetToUpdateJsonSerializable(item);
	}

	public ItemBudgetToUpdateJsonSerializable(Item item) {
		super(item);
		// TODO Auto-generated constructor stub
	}

	@JsonbProperty("unit_gross_value")
	public BigDecimal getUnitGrossValue() {
		return super.getPriceData().getUnitGrossValue();
	}
}
