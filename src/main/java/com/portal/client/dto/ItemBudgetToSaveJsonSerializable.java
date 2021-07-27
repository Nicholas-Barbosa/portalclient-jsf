package com.portal.client.dto;

import java.math.BigDecimal;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;

public class ItemBudgetToSaveJsonSerializable implements Comparable<ItemBudgetToSaveJsonSerializable> {

	@JsonbTransient
	private ItemBudgetToSave item;
	@JsonbTransient
	private ItemBudgetToSaveValues values;
	@JsonbTransient
	private Product itemProduct;

	public ItemBudgetToSaveJsonSerializable(ItemBudgetToSave item) {
		super();
		this.item = item;
		this.values = item.getValues();
		this.itemProduct = item.getProduct();
	}

	@JsonbProperty("line_discount")
	public BigDecimal getLineDiscount() {
		return values.getLineDiscount();

	}

	@JsonbProperty("product_code")
	public String getProductCode() {
		return itemProduct.getCode();
	}

	@JsonbProperty("commercial_code")
	public String getProductCommercialCode() {
		return itemProduct.getCommercialCode();
	}

	@JsonbProperty("unit_price")
	public BigDecimal getProductUnitValue() {
		return values.getUnitValue();
	}

	@JsonbProperty("st_value")
	public BigDecimal getProductStValue() {
		return values.getTotalStValue();
	}

	@JsonbProperty("quantity")
	private int getQuantity() {
		return values.getQuantity();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((itemProduct == null) ? 0 : itemProduct.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemBudgetToSaveJsonSerializable other = (ItemBudgetToSaveJsonSerializable) obj;
		if (itemProduct == null) {
			if (other.itemProduct != null)
				return false;
		} else if (!itemProduct.equals(other.itemProduct))
			return false;
		return true;
	}

	@Override
	public int compareTo(ItemBudgetToSaveJsonSerializable o) {
		// TODO Auto-generated method stub
		return o.getProductCommercialCode().compareTo(o.getProductCommercialCode());
	}

}
