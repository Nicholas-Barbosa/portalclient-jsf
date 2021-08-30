package com.portal.client.dto;

import java.math.BigDecimal;

import javax.json.bind.annotation.JsonbProperty;

import com.portal.client.vo.ItemBudget;

public class ItemBudgetToSaveJsonSerializable extends ItemBudget
		implements Comparable<ItemBudgetToSaveJsonSerializable> {

	public ItemBudgetToSaveJsonSerializable(ItemBudget item) {
		super(item);
	}

	@JsonbProperty("line_discount")
	public BigDecimal getLineDiscount() {
		return super.getValue().getLineDiscount();

	}

	@JsonbProperty("product_code")
	public String getProductCode() {
		return super.getProduct().getCode();
	}

	@JsonbProperty("commercial_code")
	public String getProductCommercialCode() {
		return super.getProduct().getCommercialCode();
	}

	@JsonbProperty("unit_price")
	public BigDecimal getProductUnitValue() {
		return super.getValue().getUnitValue();
	}

	@JsonbProperty("st_value")
	public BigDecimal getProductStValue() {
		return super.getValue().getTotalStValue();
	}

	@JsonbProperty("quantity")
	private int getQuantity() {
		return super.getValue().getQuantity();
	}

	@Override
	public int compareTo(ItemBudgetToSaveJsonSerializable o) {
		return Integer.valueOf(o.getQuantity()).compareTo(getQuantity());
	}

}
