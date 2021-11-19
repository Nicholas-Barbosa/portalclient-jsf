package com.portal.client.dto;

import java.math.BigDecimal;

import javax.json.bind.annotation.JsonbProperty;

import com.portal.client.vo.Item;
import com.portal.client.vo.Product;
import com.portal.client.vo.ProductDiscountData;
import com.portal.client.vo.ProductPriceData;

public class ItemBudgetToSaveJsonSerializable implements Comparable<ItemBudgetToSaveJsonSerializable> {

	private Product product;
	private ProductPriceData priceData;

	public static ItemBudgetToSaveJsonSerializable of(Item item) {
		return new ItemBudgetToSaveJsonSerializable(item);
	}

	public ItemBudgetToSaveJsonSerializable(Item item) {
		super();
		this.product = item.getProduct();
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
