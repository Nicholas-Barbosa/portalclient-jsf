package com.portal.dto;

public class ItemBudgetFormGssDTO {

	private String code;
	private String descriptionType;
	private String commercialCode;
	private String type;
	private String description;
	private Integer quantity;
	
	public ItemBudgetFormGssDTO(String code, String descriptionType, String commercialCode, String type,
			String description, Integer quantity) {
		super();
		this.code = code;
		this.descriptionType = descriptionType;
		this.commercialCode = commercialCode;
		this.type = type;
		this.description = description;
		this.quantity = quantity;
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
	public Integer getQuantity() {
		return quantity;
	}

	

}
