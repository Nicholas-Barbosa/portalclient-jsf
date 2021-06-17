package com.portal.java.dto;

import java.math.BigDecimal;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class Product {

	private final String code;

	private final String descriptionType;

	private final String commercialCode;

	private final String type;

	private final String description;

	private int avaliableStock;

	private final ProductInfo info;

	private final ProductPrice price;

	private final int multiple;

	@JsonbCreator
	public Product(@JsonbProperty("code") String code,
			@JsonbProperty("description_product_type") String descriptionType,
			@JsonbProperty("commercial_code") String commercialCode, @JsonbProperty("product_type") String type,
			@JsonbProperty("description") String description, @JsonbProperty("multiple") Integer multiple,
			@JsonbProperty("st_value") BigDecimal stValue, @JsonbProperty("unit_price") BigDecimal unitValue,
			@JsonbProperty("unit_gross_value") BigDecimal grossValue, @JsonbProperty("stock") int stock) {
		this.code = code;
		this.descriptionType = descriptionType;
		this.commercialCode = commercialCode;
		this.type = type;
		this.description = description;
		this.price = new ProductPrice(stValue, unitValue, grossValue);
		this.avaliableStock = stock;
		this.multiple = multiple;
		this.info = new ProductInfo();
	}

	public Product(Product product) {
		super();
		this.code = product.code;
		this.descriptionType = product.descriptionType;
		this.commercialCode = product.commercialCode;
		this.type = product.type;
		this.description = product.description;
		this.info = product.info;
		this.price = product.price;
		this.avaliableStock = product.avaliableStock;
		this.multiple = product.multiple;
	}

	public String getCode() {
		return code;
	}

	public String getDescriptionType() {
		return descriptionType;
	}

	public String getCommercialCode() {
		return commercialCode;
	}

	public String getType() {
		return type;
	}

	public String getDescription() {
		return description;
	}

	public int getAvaliableStock() {
		return avaliableStock;
	}

	public void setAvaliableStock(int avaliableStock) {
		this.avaliableStock = avaliableStock;
	}

	public ProductInfo getInfo() {
		return info;
	}

	public ProductPrice getPrice() {
		return price;
	}

	public int getMultiple() {
		return multiple;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((commercialCode == null) ? 0 : commercialCode.hashCode());
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
		Product other = (Product) obj;
		if (commercialCode == null) {
			if (other.commercialCode != null)
				return false;
		} else if (!commercialCode.equals(other.commercialCode))
			return false;
		return true;
	}

	public static class ProductPrice {
		private final BigDecimal unitStValue;
		private final BigDecimal unitValue;
		private final BigDecimal unitGrossValue;

		public ProductPrice(BigDecimal unitStValue, BigDecimal unitValue, BigDecimal unitGrossValue) {
			super();
			this.unitStValue = unitStValue;
			this.unitValue = unitValue;
			this.unitGrossValue = unitGrossValue;
		}

		public BigDecimal getUnitStValue() {
			return unitStValue;
		}

		public BigDecimal getUnitValue() {
			return unitValue;
		}

		public BigDecimal getUnitGrossValue() {
			return unitGrossValue;
		}

	}
}
