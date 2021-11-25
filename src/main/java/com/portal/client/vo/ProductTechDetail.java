package com.portal.client.vo;

public class ProductTechDetail {

	private String productsReplaces;
	private String application;
	private String alternatorCode;
	private float grossWeight;
	private String packageDimension;

	public ProductTechDetail() {
		// TODO Auto-generated constructor stub
	}
	
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
	

	public void setProductsReplaces(String productsReplaces) {
		this.productsReplaces = productsReplaces;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public void setAlternatorCode(String alternatorCode) {
		this.alternatorCode = alternatorCode;
	}

	public void setGrossWeight(float grossWeight) {
		this.grossWeight = grossWeight;
	}

	public void setPackageDimension(String packageDimension) {
		this.packageDimension = packageDimension;
	}

	@Override
	public String toString() {
		return "ProductTechDetail [productsReplaces=" + productsReplaces + ", application=" + application
				+ ", alternatorCode=" + alternatorCode + ", grossWeight=" + grossWeight + ", packageDimension="
				+ packageDimension + "]";
	}

}
