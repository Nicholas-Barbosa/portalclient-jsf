package com.portal.java.dto;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class Product {

	private String code;

	private String descriptionType;

	private String commercialCode;

	private String type;

	private String description;

	private int avaliableStock;

	private ProductInfo info;

	private ProductPrice price;

	public Product() {
	}

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
		this.price = new ProductPrice(stValue, unitValue, grossValue, stValue, unitValue, grossValue, multiple, 1,
				BigDecimal.ZERO);
		this.avaliableStock = stock;
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

	public void setInfo(ProductInfo info) {
		this.info = info;
	}

	public ProductPrice getPrice() {
		return price;
	}

	public void setPrice(ProductPrice price) {
		this.price = price;
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
		private BigDecimal unitStValue;
		private BigDecimal unitValue;
		private BigDecimal unitGrossValue;

		private BigDecimal totalStValue;
		private BigDecimal totalValue;
		private BigDecimal totalGrossValue;

		private Integer multiple;
		private int quantity;
		private BigDecimal discount;

		private final Map<String, BigDecimal> unitValuesHolderWithoutDiscount = new ConcurrentHashMap<>();

		public ProductPrice() {
			// TODO Auto-generated constructor stub
		}

		public ProductPrice(BigDecimal unitStValue, BigDecimal unitValue, BigDecimal unitGrossValue,
				BigDecimal totalStValue, BigDecimal totalValue, BigDecimal totalGrossValue, Integer multiple,
				int quantity, BigDecimal discount) {
			super();
			this.unitStValue = unitStValue;
			this.unitValue = unitValue;
			this.unitGrossValue = unitGrossValue;
			this.totalStValue = totalStValue;
			this.totalValue = totalValue;
			this.totalGrossValue = totalGrossValue;
			this.multiple = multiple;
			this.quantity = quantity;
			this.discount = discount;
			putUnitValuesOnMapHolder();
		}

		public ProductPrice(ProductPrice price) {
			super();
			this.unitStValue = price.unitStValue;
			this.unitValue = price.unitValue;
			this.unitGrossValue = price.unitGrossValue;
			this.totalStValue = price.totalStValue;
			this.totalValue = price.totalValue;
			this.totalGrossValue = price.totalGrossValue;
			this.multiple = price.multiple;
			this.quantity = price.quantity;
			this.discount = price.discount;
			putUnitValuesOnMapHolder();
		}

		private void putUnitValuesOnMapHolder() {
			unitValuesHolderWithoutDiscount.put("unitStValue", unitStValue);
			unitValuesHolderWithoutDiscount.put("unitValue", unitValue);
			unitValuesHolderWithoutDiscount.put("unitGrossValue", unitGrossValue);
		}

		public BigDecimal getUnitStValue() {
			return unitStValue;
		}

		public void setUnitStValue(BigDecimal unitStValue) {
			this.unitStValue = unitStValue;
		}

		public BigDecimal getUnitValue() {
			return unitValue;
		}

		public void setUnitValue(BigDecimal unitValue) {
			this.unitValue = unitValue;
		}

		public BigDecimal getUnitGrossValue() {
			return unitGrossValue;
		}

		public void setUnitGrossValue(BigDecimal unitGrossValue) {
			this.unitGrossValue = unitGrossValue;
		}

		public BigDecimal getTotalStValue() {
			return totalStValue;
		}

		public void setTotalStValue(BigDecimal totalStValue) {
			this.totalStValue = totalStValue;
		}

		public BigDecimal getTotalValue() {
			return totalValue;
		}

		public void setTotalValue(BigDecimal totalValue) {
			this.totalValue = totalValue;
		}

		public BigDecimal getTotalGrossValue() {
			return totalGrossValue;
		}

		public void setTotalGrossValue(BigDecimal totalGrossValue) {
			this.totalGrossValue = totalGrossValue;
		}

		public Integer getMultiple() {
			return multiple;
		}

		public void setMultiple(Integer multiple) {
			this.multiple = multiple;
		}

		public int getQuantity() {
			return quantity;
		}

		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}

		public BigDecimal getDiscount() {
			return discount;
		}

		public void setDiscount(BigDecimal discount) {
			this.discount = discount;
		}

		public BigDecimal getUnitStValueWithNoDiscount() {
			return this.getValueWithNoDiscount("unitStValue");
		}

		public BigDecimal getUnitValueWithNoDiscount() {
			return this.getValueWithNoDiscount("unitValue");
		}

		public BigDecimal getUnitGrossValueWithNoDiscount() {
			return this.getValueWithNoDiscount("unitGrossValue");
		}

		public BigDecimal getValueWithNoDiscount(String field) {
			return unitValuesHolderWithoutDiscount.get(field);
		}
	}
}
