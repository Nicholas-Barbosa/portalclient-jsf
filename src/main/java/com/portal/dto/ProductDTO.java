package com.portal.dto;

import java.math.BigDecimal;

public class ProductDTO {

	private String code;

	private String descriptionType;

	private String commercialCode;

	private String type;

	private String description;

	private ProductPrice price;

	private Integer multiple;

	private int quantity;

	public ProductDTO() {
		// TODO Auto-generated constructor stub
	}

	public ProductDTO(String code, String descriptionType, String commercialCode, String type, String description,
			Integer multiple, int quantity, ProductPrice price) {
		super();
		this.code = code;
		this.descriptionType = descriptionType;
		this.commercialCode = commercialCode;
		this.type = type;
		this.description = description;
		this.multiple = multiple;
		this.quantity = quantity;
		this.price = price;
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

	public ProductPrice getPrice() {
		return price;
	}

	public Integer getMultiple() {
		return multiple;
	}

	public int getQuantity() {
		return quantity;
	}

	@Override
	public String toString() {
		return "ProductDTO [code=" + code + ", descriptionType=" + descriptionType + ", commercialCode="
				+ commercialCode + ", type=" + type + ", description=" + description + ", price=" + price
				+ ", multiple=" + multiple + ", quantity=" + quantity + "]";
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

	public static class ProductPrice {

		private BigDecimal unitGrossValue;

		private BigDecimal totalGrossValue;

		private String productCode;

		private String commercialCode;

		private BigDecimal unitPrice;

		private BigDecimal totale;

		private BigDecimal stValue;

		private BigDecimal unitStValue;

		private BigDecimal discount;

		private int avaliableStock;

		public ProductPrice(BigDecimal unitGrossValue, BigDecimal totalGrossValue, String productCode,
				String commercialCode, BigDecimal unitPrice, BigDecimal totale, BigDecimal stValue,
				BigDecimal unitStValue, BigDecimal discount, int avaliableStock) {
			super();
			this.unitGrossValue = unitGrossValue;
			this.totalGrossValue = totalGrossValue;
			this.productCode = productCode;
			this.commercialCode = commercialCode;
			this.unitPrice = unitPrice;
			this.totale = totale;
			this.stValue = stValue;
			this.unitStValue = unitStValue;
			this.discount = discount;
			this.avaliableStock = avaliableStock;
		}

		public BigDecimal getUnitGrossValue() {
			return unitGrossValue;
		}

		public BigDecimal getTotalGrossValue() {
			return totalGrossValue;
		}

		public String getProductCode() {
			return productCode;
		}

		public String getCommercialCode() {
			return commercialCode;
		}

		public BigDecimal getUnitPrice() {
			return unitPrice;
		}

		public BigDecimal getTotale() {
			return totale;
		}

		public BigDecimal getStValue() {
			return stValue;
		}

		public BigDecimal getUnitStValue() {
			return unitStValue;
		}

		public BigDecimal getDiscount() {
			return discount;
		}

		public int getAvaliableStock() {
			return avaliableStock;
		}

	}
}
