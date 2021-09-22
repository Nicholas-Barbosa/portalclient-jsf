package com.portal.client.vo.builder;

import com.portal.client.vo.ProductImage;
import com.portal.client.vo.ProductImage.ImageInfoState;

public class ProductImageBuilder implements ContractProductImageBuilder {

	private ProductImage image;

	public ProductImageBuilder() {
		image = new ProductImage();
	}

	public static ProductImageBuilder getInstance() {
		return new ProductImageBuilder();

	}

	@Override
	public ContractProductImageBuilder withImage(byte[] image) {
		this.image.setImageStreams(image);
		return this;
	}

	@Override
	public ContractProductImageBuilder withState(ImageInfoState state) {
		this.image.setCurrentState(state);
		return this;
	}

	@Override
	public ProductImage build() {
		return image;
	}

}
