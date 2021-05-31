package com.portal.dto;

public class ProductInfoDTO {

	private byte[] image;

	public ProductInfoDTO() {
		// TODO Auto-generated constructor stub
	}

	public ProductInfoDTO(byte[] image) {
		super();
		this.image = image.clone();
	}

	public ProductInfoDTO(ProductInfoDTO info) {
		this.image = info.image == null? new byte[0] : info.image.clone();
	}

	public byte[] getImage() {
		System.out.println("get image!");
		return image.clone();
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
}
