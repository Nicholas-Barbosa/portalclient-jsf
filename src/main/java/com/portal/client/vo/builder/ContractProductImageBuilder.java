package com.portal.client.vo.builder;

import com.portal.client.vo.ProductImage;
import com.portal.client.vo.ProductImage.ImageInfoState;

public interface ContractProductImageBuilder {

	ContractProductImageBuilder withImage(byte[] image);

	ContractProductImageBuilder withState(ImageInfoState state);

	ProductImage build();
}
