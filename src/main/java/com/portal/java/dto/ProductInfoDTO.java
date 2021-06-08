package com.portal.java.dto;

public class ProductInfoDTO {

	private ImageInfoDTO imageInfoDTO;

	public ProductInfoDTO() {

	}

	public ProductInfoDTO(byte[] image) {
		super();
		this.imageInfoDTO = new ImageInfoDTO(image);
	}

	public void setImage(byte[] image) {
		this.imageInfoDTO.setImage(image);
		imageInfoDTO.setStateDueImageStreams();
	}

	public byte[] getImage() {
		return this.imageInfoDTO.getImage();
	}

	public ImageInfoState getStateForImage() {
		return imageInfoDTO.currentState;
	}
	public void setStateForImage(ImageInfoState state) {
		imageInfoDTO.currentState = state;
	}

	public class ImageInfoDTO {
		private byte[] image;
		private ImageInfoState currentState;

		public ImageInfoDTO(byte[] image, ImageInfoState currentState) {
			super();
			this.image = image.clone();
			setStateDueImageStreams();
		}

		public ImageInfoDTO(byte[] image) {
			super();
			this.image = image.clone();
			setStateDueImageStreams();
		}

		public ImageInfoDTO() {
			this.image = new byte[0];
		}

		public byte[] getImage() {
			return image.clone();
		}

		public void setImage(byte[] image) {
			this.image = image.clone();
		}

		public ImageInfoState getCurrentState() {
			return currentState;
		}

		public void setCurrentState(ImageInfoState currentState) {
			this.currentState = currentState;
		}

		private final void setStateDueImageStreams() {
			switch (image.length) {
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
	}

	public static enum ImageInfoState {
		FOUND, NOT_FOUND, TIMEOUT_EXCPTION
	}
}
