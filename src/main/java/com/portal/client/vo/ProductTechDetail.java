package com.portal.client.vo;

public class ProductTechDetail {

	private String productsReplaces;
	private String application;
	private String alternatorCode;
	private float grossWeight;
	private String packageDimension;

	public ProductTechDetail(String productsReplaces, String application, String alternatorCode, float grossWeight,
			String packageDimension) {
		super();
		this.productsReplaces = productsReplaces;
		this.application = application;
		this.alternatorCode = alternatorCode;
		this.grossWeight = grossWeight;
		this.packageDimension = packageDimension;
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

	public float getGrossWeight() {
		return grossWeight;
	}

	public String getPackageDimension() {
		return packageDimension;
	}

	@Override
	public String toString() {
		return "ProductTechDetail [productsReplaces=" + productsReplaces + ", application=" + application
				+ ", alternatorCode=" + alternatorCode + ", grossWeight=" + grossWeight + ", packageDimension="
				+ packageDimension + "]";
	}

}
