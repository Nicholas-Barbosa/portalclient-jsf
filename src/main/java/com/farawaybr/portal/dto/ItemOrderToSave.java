package com.farawaybr.portal.dto;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;

import com.farawaybr.portal.vo.Item;
import com.farawaybr.portal.vo.Product;
import com.farawaybr.portal.vo.ProductPriceData;

public class ItemOrderToSave {

	@JsonbTransient
	private Item item;

	@JsonbTransient
	private final Product itemProduct;

	@JsonbTransient
	private final ProductPriceData value;

	public ItemOrderToSave(Item item) {
		super();
		this.item = item;
		this.itemProduct = item;
		this.value = itemProduct.getPriceData();
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

	@JsonbProperty("client_order")
	public String getClientOrder() {
		return item.getCustomerOrder();
	}
//	public int getQuantity() {
//		return value.getQuantity();
//	}
}
