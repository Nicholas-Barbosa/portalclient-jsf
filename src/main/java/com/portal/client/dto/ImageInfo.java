package com.portal.client.dto;

public class ImageInfo {
	private byte[] imageStreams;
	private ImageInfoState currentState;

	public ImageInfo(byte[] image, ImageInfoState currentState) {
		super();
		this.imageStreams = image.clone();
		setStateDueImageStreams();
	}

	public ImageInfo(byte[] image) {
		super();
		this.imageStreams = image.clone();
		setStateDueImageStreams();
	}

	public ImageInfo() {
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