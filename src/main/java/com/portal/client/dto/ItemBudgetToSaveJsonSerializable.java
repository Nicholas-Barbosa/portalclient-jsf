package com.portal.client.dto;

import java.math.BigDecimal;

import javax.json.bind.annotation.JsonbProperty;

import com.portal.client.vo.Item;

public class ItemBudgetToSaveJsonSerializable implements Comparable<ItemBudgetToSaveJsonSerializable> {

	private Item item;

	public static ItemBudgetToSaveJsonSerializable of(Item item) {
		return new ItemBudgetToSaveJsonSerializable(item);
	}

	public ItemBudgetToSaveJsonSerializable(Item item) {
		super();
		this.item = item;
	}

	@JsonbProperty("line_discount")
	public BigDecimal getLineDiscount() {
		BigDecimal lineDiscount = item.getValue().getLineDiscount();
		return lineDiscount == null ? BigDecimal.ZERO : lineDiscount;

	}

	@JsonbProperty("product_code")
	public String getProductCode() {
		return item.getProduct().getCode();
	}

	@JsonbProperty("commercial_code")
	public String getProductCommercialCode() {
		return item.getProduct().getCommercialCode();
	}

	@JsonbProperty("unit_price")
	public BigDecimal getProductUnitValue() {
		return item.getValue().getUnitValue();
	}

	@JsonbProperty("st_value")
	public BigDecimal getProductStValue() {
		return item.getValue().getTotalStValue();
	}

	@JsonbProperty("quantity")
	private int getQuantity() {
		return item.getValue().getQuantity();
	}

	@Override
	public int compareTo(ItemBudgetToSaveJsonSerializable o) {
		return Integer.valueOf(o.getQuantity()).compareTo(getQuantity());
	}

}
