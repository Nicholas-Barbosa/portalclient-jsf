package com.farawaybr.portal.dto;

import java.math.BigDecimal;

import javax.json.bind.annotation.JsonbProperty;

import com.farawaybr.portal.vo.Item;
import com.farawaybr.portal.vo.Product;
import com.farawaybr.portal.vo.ProductDiscountData;
import com.farawaybr.portal.vo.ProductPriceData;

public class ItemBudgetToSaveJsonSerializable implements Comparable<ItemBudgetToSaveJsonSerializable> {

	private Item product;
	private ProductPriceData priceData;

	public static ItemBudgetToSaveJsonSerializable of(Item item) {
		return new ItemBudgetToSaveJsonSerializable(item);
	}

	public ItemBudgetToSaveJsonSerializable(Item item) {
		super();
		this.product = item;
		this.priceData = product.getPriceData();
	}

	@JsonbProperty("line_discount")
	public float getLineDiscount() {
		ProductDiscountData discountData = product.getPriceData().getDiscountData();
		float lineDiscount = discountData == null ? 0f : discountData.getDiscount();
		return lineDiscount;

	}

	@JsonbProperty("product_code")
	public String getProductCode() {
		return product.getCode();
	}

	@JsonbProperty("commercial_code")
	public String getProductCommercialCode() {
		return product.getCommercialCode();
	}

	@JsonbProperty("unit_price")
	public BigDecimal getProductUnitValue() {
		return priceData.getUnitValue();
	}

	@JsonbProperty("st_value")
	public BigDecimal getProductStValue() {
		return priceData.getTotalStValue();
	}

	@JsonbProperty("quantity")
	private int getQuantity() {
		return priceData.getQuantity();
	}

	@Override
	public int compareTo(ItemBudgetToSaveJsonSerializable o) {
		return Integer.valueOf(o.getQuantity()).compareTo(getQuantity());
	}

	public Product getProduct() {
		return product;
	}

	public ProductPriceData getPriceData() {
		return priceData;
	}
}
