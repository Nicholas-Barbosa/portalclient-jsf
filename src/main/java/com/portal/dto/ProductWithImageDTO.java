package com.portal.dto;

public class ProductWithImageDTO extends BaseProductDTO {

	private byte[] image;

	public ProductWithImageDTO() {
		// TODO Auto-generated constructor stub
	}

	public ProductWithImageDTO(String code, String descriptionType, String commercialCode, String type, String description,
			Integer multiple) {
		super(code, null, commercialCode, null, null, null);

	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
	
	
}
