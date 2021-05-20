package com.portal.dto;

import javax.json.bind.annotation.JsonbProperty;

public class ItemEstimateBudgetForm implements Comparable<ItemEstimateBudgetForm> {

	private String code;
	private String descriptionType;
	@JsonbProperty("product_code")
	private String commercialCode;
	private String description;
	@JsonbProperty
	private Integer quantity;

	public ItemEstimateBudgetForm() {
		// TODO Auto-generated constructor stub
	}

	public ItemEstimateBudgetForm(String code, String descriptionType, String commercialCode, String description,
			Integer quantity) {
		super();
		this.code = code;
		this.descriptionType = descriptionType;
		this.commercialCode = commercialCode;
		this.description = description;
		this.quantity = quantity;
	}

	public static ItemEstimateBudgetForm of(ProductQueryDTO product) {
		return new ItemEstimateBudgetForm(product.getCode(), product.getDescriptionType(), product.getCommercialCode(),
				product.getDescription(), 1);
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

	public String getDescription() {
		return description;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
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
		ItemEstimateBudgetForm other = (ItemEstimateBudgetForm) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (commercialCode == null) {
			if (other.commercialCode != null)
				return false;
		} else if (!commercialCode.equals(other.commercialCode))
			return false;
		return true;
	}

	@Override
	public int compareTo(ItemEstimateBudgetForm o) {
		// TODO Auto-generated method stub
		return commercialCode.compareTo(o.commercialCode);
	}

	@Override
	public String toString() {
		return "ItemEstimateBudgetForm [code=" + code + ", quantity=" + quantity + "]";
	}

}
