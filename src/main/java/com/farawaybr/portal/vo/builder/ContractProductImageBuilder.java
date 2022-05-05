package com.farawaybr.portal.vo.builder;

import com.farawaybr.portal.vo.ProductImage;
import com.farawaybr.portal.vo.ProductImage.ImageInfoState;

public interface ContractProductImageBuilder {

	ContractProductImageBuilder withImage(byte[] image);

	ContractProductImageBuilder withState(ImageInfoState state);

	ProductImage build();
}
