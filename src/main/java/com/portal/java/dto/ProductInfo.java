package com.portal.java.dto;

public class ProductInfo {

	private ImageInfo imageInfo;

	public ProductInfo() {

	}

	public ProductInfo(ImageInfo imageInfoDTO) {
		super();
		this.imageInfo = imageInfoDTO;
	}

	public ProductInfo(byte[] image) {
		super();
		this.imageInfo = new ImageInfo(image);
	}

	public ImageInfo getImageInfo() {
		return imageInfo;
	}

	public void setImageInfo(ImageInfo imageInfoDTO) {
		this.imageInfo = imageInfoDTO;
	}


}
