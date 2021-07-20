package com.portal.client.vo;

public class ProductImage {
	private byte[] imageStreams;
	private ImageInfoState currentState;

	public ProductImage(byte[] image, ImageInfoState currentState) {
		super();
		this.imageStreams = image.clone();
		setStateDueImageStreams();
	}

	public ProductImage(byte[] image) {
		super();
		this.imageStreams = image.clone();
		setStateDueImageStreams();
	}

	public ProductImage() {
		this.imageStreams = new byte[0];
	}

	public byte[] getImageStreams() {
		return imageStreams.clone();
	}

	public void setImageStreams(byte[] image) {
		this.imageStreams = image.clone();
		setStateDueImageStreams();
	}

	public ImageInfoState getCurrentState() {
		return currentState;
	}

	public void setCurrentState(ImageInfoState currentState) {
		this.currentState = currentState;
	}

	private final void setStateDueImageStreams() {
		switch (imageStreams.length) {
		case 0:
			this.currentState = ImageInfoState.NOT_FOUND;
			break;

		case 1:
			currentState = ImageInfoState.TIMEOUT_EXCPTION;
			break;
		default:
			this.currentState = ImageInfoState.FOUND;
			break;
		}

	}

	public static enum ImageInfoState {
		FOUND, NOT_FOUND, TIMEOUT_EXCPTION
	}

}
