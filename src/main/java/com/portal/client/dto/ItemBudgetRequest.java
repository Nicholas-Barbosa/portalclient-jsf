package com.portal.client.dto;

import java.math.BigDecimal;

import javax.json.bind.annotation.JsonbProperty;

import com.portal.client.vo.Product;

public class ItemBudgetRequest {

	private final Product product;
	private ItemBuRequestValues values;

	public ItemBudgetRequest(BigDecimal budgetGlobalDiscount, BigDecimal lineDiscount, Product product,
			ItemBuRequestValues itemPrice) {
		super();
		this.product = product;
		this.values = itemPrice;
	}

	public ItemBudgetRequest() {
		this(BigDecimal.ZERO, BigDecimal.ZERO, null, null);
	}

	public Product getProduct() {
		return product;
	}

	public ItemBuRequestValues getValues() {
		return values;
	}

	public String line() {
		return product.getLine();
	}

	@JsonbProperty("line_discount")
	public BigDecimal getLineDiscount() {
		return values.getLineDiscount();
	}

	@JsonbProperty("product_code")
	public String getProductCode() {
		return product.getCode();
	}

	@JsonbProperty("commercial_code")
	public String getCommercialCode() {
		return product.getCommercialCode();
	}

	@JsonbProperty("unit_price")
	public BigDecimal getUnitValue() {
		return values.getUnitValue();
	}

	@JsonbProperty("quantity")
	public int getQuantity() {
		return values.getQuantity();
	}

	@JsonbProperty("st_value")
	public BigDecimal getStValues() {
		return values.getTotalStValue();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((product == null) ? 0 : product.hashCode());
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
		ItemBudgetRequest other = (ItemBudgetRequest) obj;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		return true;
	}

}
