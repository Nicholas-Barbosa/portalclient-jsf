package com.portal.client.dto.builder;

import java.math.BigDecimal;

import com.portal.client.dto.ProductValue;
import com.portal.client.vo.Product;
import com.portal.client.vo.ProductImage;
import com.portal.client.vo.ProductTechDetail;

public class ProductBuilder {

	private String code, commercialCode, applicability, description, line, acronymLine;
	private int multiple;
	private boolean commercialBlock;
	private ProductImage productImage;
	private ProductTechDetail techDetail;

	private BigDecimal unitStValue, unitValue, unitGrossValue;

	public static ProductBuilder getInstance() {
		return new ProductBuilder();
	}

	public ProductBuilder withCode(String code) {
		this.code = code;
		return this;
	}

	public ProductBuilder withCommercialCode(String commercialCode) {
		this.commercialCode = commercialCode;
		return this;
	}

	public ProductBuilder withApplicability(String applicability) {
		this.applicability = applicability;
		return this;
	}

	public ProductBuilder withLine(String line) {
		this.line = line;
		return this;
	}

	public ProductBuilder withAcronymLine(String acronymLine) {
		this.acronymLine = acronymLine;
		return this;
	}

	public ProductBuilder withMultiple(int multiple) {
		this.multiple = multiple;
		return this;
	}

	public ProductBuilder withCommercialBlock(boolean commercialBlock) {
		this.commercialBlock = commercialBlock;
		return this;
	}

	public ProductBuilder withImage(byte[] image) {
		productImage.setImageStreams(image);
		return this;
	}

	public ProductBuilder withUnitStValue(BigDecimal unitStValue) {
		this.unitStValue = unitStValue;
		return this;
	}

	public ProductBuilder withUnitValue(BigDecimal unitValue) {
		this.unitValue = unitValue;
		return this;
	}

	public ProductBuilder withUnitGrossValue(BigDecimal unitGrossValue) {
		this.unitGrossValue = unitGrossValue;
		return this;
	}

	public ProductBuilder withDescription(String description) {
		this.description = description;
		return this;
	}

	public ProductBuilder withTechDetail(ProductTechDetail t) {
		this.techDetail = t;
		return this;
	}

	public Product build() {
		return new Product(code, commercialCode, applicability, description, line, acronymLine, multiple, multiple,
				commercialBlock, productImage, this.buildValue(), techDetail);
	}

	public ProductValue buildValue() {
		return new ProductValue(unitStValue, unitValue, unitGrossValue);
	}
}
