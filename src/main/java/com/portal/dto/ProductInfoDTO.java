package com.portal.dto;

public class ProductInfoDTO {

	private byte[] image;

	public ProductInfoDTO() {
		this.image = new byte[0];
	}

	public ProductInfoDTO(byte[] image) {
		super();
		this.image = image.clone();
	}

	public ProductInfoDTO(ProductInfoDTO info) {
		this.image = info.image == null? new byte[0] : info.image.clone();
	}

	public byte[] getImage() {
		return image.clone();
	}

	public void setImage(byte[] image) {
		this.image = image.clone();
	}
}
