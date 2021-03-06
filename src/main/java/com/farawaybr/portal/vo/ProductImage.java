package com.farawaybr.portal.vo;

public class ProductImage {
	private byte[] imageStreams;
	private ImageInfoState currentState;

	public ProductImage(byte[] image, ImageInfoState currentState) {
		super();
		this.imageStreams = image != null ? image.clone() : new byte[0];
		this.currentState = currentState;
	}

	public ProductImage(byte[] image) {
		super();
		this.imageStreams = image.clone();
	}

	public ProductImage() {
		this.imageStreams = new byte[0];
	}

	public ProductImage(ProductImage image) {
		this(image.imageStreams,image.currentState);
	}

	public byte[] getImageStreams() {
		return imageStreams.clone();
	}

	public void setImageStreams(byte[] image) {
		if (image != null)
			this.imageStreams = image.clone();

	}

	public ImageInfoState getCurrentState() {
		return currentState;
	}

	public void setCurrentState(ImageInfoState currentState) {
		this.currentState = currentState;
	}

	public static enum ImageInfoState {
		FOUND, NOT_FOUND, TIMEOUT_EXCPTION, NOT_LOADED
	}

}
