package com.portal.java.dto;

import java.math.BigDecimal;

public class BaseProductDTO implements Comparable<BaseProductDTO> {

	private String code;

	private String descriptionType;

	private String commercialCode;

	private String type;

	private String description;

	private Integer multiple;

	private int quantity;

	private BigDecimal discount;

	private ProductInfoDTO info;

	public BaseProductDTO() {
		// TODO Auto-generated constructor stub
	}

	public BaseProductDTO(String code, String descriptionType, String commercialCode, String type, String description,
			Integer multiple) {
		super();
		this.code = code;
		this.descriptionType = descriptionType;
		this.commercialCode = commercialCode;
		this.type = type;
		this.description = description;
		this.multiple = multiple;
	}

	public BaseProductDTO(String commercialCode, String description, Integer multiple, int quantity) {
		super();
		this.commercialCode = commercialCode;
		this.description = description;
		this.multiple = multiple;
		this.quantity = quantity;
	}

	public BaseProductDTO(String code, String descriptionType, String commercialCode, String type, String description,
			Integer multiple, int quantity, ProductInfoDTO info) {
		super();
		this.code = code;
		this.descriptionType = descriptionType;
		this.commercialCode = commercialCode;
		this.type = type;
		this.description = description;
		this.multiple = multiple;
		this.quantity = quantity;
		this.info = info;
	}

	public BaseProductDTO(String commercialCode, int quantity, BigDecimal discount) {
		super();
		this.commercialCode = commercialCode;
		this.quantity = quantity;
		this.discount = discount;
	}

	public BaseProductDTO(String code, String commercialCode, int quantity, BigDecimal discount) {
		super();
		this.code = code;
		this.commercialCode = commercialCode;
		this.quantity = quantity;
		this.discount = discount;
	}

	public BaseProductDTO(BaseProductDTO b) {
		this.code = b.code;
		this.descriptionType = b.descriptionType;
		this.commercialCode = b.commercialCode;
		this.type = b.type;
		this.description = b.description;
		this.multiple = b.multiple;
		this.quantity = b.quantity;
		this.discount = b.discount;
		this.info = b.info;
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

	public Integer getMultiple() {
		return multiple;
	}

	protected void setCode(String code) {
		this.code = code;
	}

	protected void setDescriptionType(String descriptionType) {
		this.descriptionType = descriptionType;
	}

	protected void setCommercialCode(String commercialCode) {
		this.commercialCode = commercialCode;
	}

	protected void setType(String type) {
		this.type = type;
	}

	protected void setDescription(String description) {
		this.description = description;
	}

	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getQuantity() {
		return quantity;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		System.out.println("set discount from " +this.discount +" to " +discount);
		this.discount = discount;
	}

	public ProductInfoDTO getInfo() {
		return info;
	}

	public void setInfo(ProductInfoDTO info) {
		this.info = info;
	}

	@Override
	public String toString() {
		return "ProductDTO [code=" + code + ", descriptionType=" + descriptionType + ", commercialCode="
				+ commercialCode + ", type=" + type + ", description=" + description + "]";
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
		BaseProductDTO other = (BaseProductDTO) obj;
		if (commercialCode == null) {
			if (other.commercialCode != null)
				return false;
		} else if (!commercialCode.equals(other.commercialCode))
			return false;
		return true;
	}

	@Override
	public int compareTo(BaseProductDTO o) {
		// TODO Auto-generated method stub
		return commercialCode.compareToIgnoreCase(o.commercialCode);
	}

}
