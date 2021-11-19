package com.portal.client.vo;

import java.math.BigDecimal;

public class ProductPriceData extends BaseProductPriceState {

	private int quantity;
	private Integer multiple;
	private ProductDiscountData discountData;

	public ProductPriceData() {
		// TODO Auto-generated constructor stub
	}

	public ProductPriceData(ProductPriceData priceData) {
		this(priceData.unitStValue, priceData.unitValue, priceData.unitGrossValue, priceData.totalStValue,
				priceData.totalValue, priceData.totalGrossValue, priceData.quantity, priceData.multiple,
				priceData.discountData);
	}

	public ProductPriceData(BigDecimal unitStValue, BigDecimal unitValue, BigDecimal unitGrossValue, int quantity,
			Integer multiple, ProductDiscountData discountData) {
		this(unitStValue, unitValue, unitGrossValue, null, null, null, quantity, multiple,
				discountData);
	}

	public ProductPriceData(BigDecimal unitStValue, BigDecimal unitValue, BigDecimal unitGrossValue,
			BigDecimal totalStValue, BigDecimal totalValue, BigDecimal totalGrossValue, int quantity, Integer multiple,
			ProductDiscountData discountData) {
		super();
		super.unitStValue = unitStValue;
		super.unitValue = unitValue;
		super.unitGrossValue = unitGrossValue;
		super.totalStValue = totalStValue;
		super.totalValue = totalValue;
		super.totalGrossValue = totalGrossValue;
		this.quantity = quantity;
		this.multiple = multiple;
		this.discountData = discountData;
	}

	


	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Integer getMultiple() {
		return multiple;
	}


	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}

	public ProductDiscountData getDiscountData() {
		return discountData;
	}

	public void setDiscountData(ProductDiscountData discountData) {
		this.discountData = discountData;
	}

	@Override
	public String toString() {
		return "ProductValue [unitStValue=" + unitStValue + ", unitValue=" + unitValue + ", unitGrossValue="
				+ unitGrossValue + ", totalStValue=" + totalStValue + ", totalValue=" + totalValue
				+ ", totalGrossValue=" + totalGrossValue + ", quantity=" + quantity + ", multiple=" + multiple + "]";
	}

}
