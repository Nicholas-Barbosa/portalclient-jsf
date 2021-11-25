package com.portal.client.vo.builder;

import com.portal.client.vo.ProductTechDetail;

public class ProductTechDetailBuilder {

	private ProductTechDetail object;

	public static ProductTechDetailBuilder getInstance() {
		return new ProductTechDetailBuilder();
	}

	private ProductTechDetailBuilder() {
		this.object = new ProductTechDetail();
	}

	public ProductTechDetailBuilder withProductsReplaces(String value) {
		object.setProductsReplaces(value);
		return this;
	}

	public ProductTechDetailBuilder withApplication(String value) {
		object.setApplication(value);
		return this;
	}

	public ProductTechDetailBuilder withAlternatorCode(String value) {
		object.setAlternatorCode(value);
		return this;
	}

	public ProductTechDetailBuilder withGrossWeight(float value) {
		object.setGrossWeight(value);
		return this;
	}

	public ProductTechDetailBuilder withPackageDimension(String value) {
		object.setPackageDimension(value);
		return this;
	}

	public ProductTechDetail build() {
		return this.object;
	}
}
