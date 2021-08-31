package com.portal.client.dto;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;

import com.portal.client.vo.Item;
import com.portal.client.vo.ItemValue;
import com.portal.client.vo.Product;

public class ItemOrderToSave {

	@JsonbTransient
	private Item item;

	@JsonbTransient
	private Product itemProduct;

	@JsonbTransient
	private ItemValue value;
	
	public ItemOrderToSave(Item item) {
		super();
		this.item = item;
	}

	@JsonbProperty("product_code")
	public String getCode() {
		return itemProduct.getCode();
	}
	
	@JsonbProperty("commercial_code")
	public String getCommercialCode() {
		return itemProduct.getCommercialCode();
	}
	
	@JsonbProperty("quantity")
	public int getQuantity() {
		return value.getQuantity();
	}
	
//	public int getQuantity() {
//		return value.getQuantity();
//	}
}
