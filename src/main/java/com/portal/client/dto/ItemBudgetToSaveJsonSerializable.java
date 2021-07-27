package com.portal.client.dto;

import java.math.BigDecimal;

import javax.json.bind.annotation.JsonbProperty;

public class ItemBudgetToSaveJsonSerializable extends ItemBudget
		implements Comparable<ItemBudgetToSaveJsonSerializable> {

	public ItemBudgetToSaveJsonSerializable(ItemBudget item) {
		super(item);
	}

	@JsonbProperty("line_discount")
	public BigDecimal getLineDiscount() {
		return super.getValues().getLineDiscount();

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
		return super.getValues().getUnitValue();
	}

	@JsonbProperty("st_value")
	public BigDecimal getProductStValue() {
		return super.getValues().getTotalStValue();
	}

	@JsonbProperty("quantity")
	private int getQuantity() {
		return super.getValues().getQuantity();
	}

	@Override
	public int compareTo(ItemBudgetToSaveJsonSerializable o) {
		// TODO Auto-generated method stub
		return o.getProductCommercialCode().compareTo(o.getProductCommercialCode());
	}

}
