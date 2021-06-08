package com.portal.java.dto;

public class ImageDTO {

	private byte[] image;

	public ImageDTO() {
		this.image = new byte[0];
	}

	public ImageDTO(byte[] image) {
		super();
		this.image = image.clone();
	}

	public ImageDTO(ImageDTO info) {
		this.image = info.image == null ? new byte[0] : info.image.clone();
	}

	public byte[] getImage() {
		return image.clone();
	}

	public void setImage(byte[] image) {
		this.image = image.clone();
	}
}
