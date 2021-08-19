package com.portal.client.dto;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

import com.portal.client.vo.ProductTechDetail;

public class ProductTechDetailJson {

	private String productsReplaces;
	private String application;
	private String alternatorCode;
	private String chain;
	private float grossWeight;
	private String packageDimension;

	@JsonbCreator
	public ProductTechDetailJson(@JsonbProperty("products_replaces") String productsReplaces,
			@JsonbProperty("products_application") String application,
			@JsonbProperty("products_codigo_alternador") String alternatorCode,
			@JsonbProperty("products_peso_bruto") String grossWeight,
			@JsonbProperty("products_dimensao_embalagem") String packageDimension) {
		this.productsReplaces = productsReplaces;
		this.application = application;
		this.alternatorCode = alternatorCode;
		this.packageDimension = packageDimension;
		this.getGrossWeight(grossWeight);
	}

	private final void getGrossWeight(String grossWeight) {

	}

	public String getProductsReplaces() {
		return productsReplaces;
	}

	public String getApplication() {
		return application;
	}

	public String getAlternatorCode() {
		return alternatorCode;
	}

	public String getChain() {
		return chain;
	}

	public float getGrossWeight() {
		return grossWeight;
	}

	public String getPackageDimension() {
		return packageDimension;
	}

	public ProductTechDetail toDetail() {
		return new ProductTechDetail(productsReplaces, application, alternatorCode, grossWeight, packageDimension);
	}

}
