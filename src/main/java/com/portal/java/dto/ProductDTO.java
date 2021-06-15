package com.portal.java.dto;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class ProductDTO {

	private String code;

	private String descriptionType;

	private String commercialCode;

	private String type;

	private String description;

	private ProductInfoDTO info;

	private ProductPriceDTO price;

	public ProductDTO() {
	}

	@JsonbCreator
	public ProductDTO(@JsonbProperty("code") String code,
			@JsonbProperty("description_product_type") String descriptionType,
			@JsonbProperty("commercial_code") String commercialCode, @JsonbProperty("product_type") String type,
			@JsonbProperty("description") String description, @JsonbProperty("multiple") Integer multiple,
			@JsonbProperty("st_value") BigDecimal stValue, @JsonbProperty("unit_price") BigDecimal unitValue,
			@JsonbProperty("unit_gross_value") BigDecimal grossValue) {
		this.code = code;
		this.descriptionType = descriptionType;
		this.commercialCode = commercialCode;
		this.type = type;
		this.description = description;
		this.price = new ProductPriceDTO(stValue, unitValue, grossValue, stValue, unitValue, grossValue, multiple, 1,
				BigDecimal.ZERO);

	}

	public ProductDTO(ProductDTO productDTO) {
		super();
		this.code = productDTO.code;
		this.descriptionType = productDTO.descriptionType;
		this.commercialCode = productDTO.commercialCode;
		this.type = productDTO.type;
		this.description = productDTO.description;
		this.info = productDTO.info;
		this.price = productDTO.price;
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

	public ProductInfoDTO getInfo() {
		return info;
	}

	public void setInfo(ProductInfoDTO info) {
		this.info = info;
	}

	public ProductPriceDTO getPrice() {
		return price;
	}

	public void setPrice(ProductPriceDTO price) {
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
		ProductDTO other = (ProductDTO) obj;
		if (commercialCode == null) {
			if (other.commercialCode != null)
				return false;
		} else if (!commercialCode.equals(other.commercialCode))
			return false;
		return true;
	}

	public static class ProductPriceDTO {
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

		public ProductPriceDTO() {
			// TODO Auto-generated constructor stub
		}

		public ProductPriceDTO(BigDecimal unitStValue, BigDecimal unitValue, BigDecimal unitGrossValue,
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

		public ProductPriceDTO(ProductPriceDTO price) {
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
