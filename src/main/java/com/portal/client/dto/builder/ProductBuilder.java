package com.portal.client.dto.builder;

import java.math.BigDecimal;

import com.portal.client.dto.ProductValue;
import com.portal.client.vo.Product;
import com.portal.client.vo.ProductImage;
import com.portal.client.vo.ProductTechDetail;

public class ProductBuilder implements ContractProductBuilder {

	private String code, commercialCode, applicability, description, line, acronymLine;
	private int quantity;
	private boolean commercialBlock;
	private ProductImage productImage;
	private ProductTechDetail techDetail;
	private Integer stock, multiple;
	private BigDecimal unitStValue, unitValue, unitGrossValue;

	public static ProductBuilder getInstance() {
		return new ProductBuilder();
	}

	@Override
	public ProductBuilder withCode(String code) {
		this.code = code;
		return this;
	}

	@Override
	public ProductBuilder withCommercialCode(String commercialCode) {
		this.commercialCode = commercialCode;
		return this;
	}

	@Override
	public ProductBuilder withApplicability(String applicability) {
		this.applicability = applicability;
		return this;
	}

	@Override
	public ProductBuilder withLine(String line) {
		this.line = line;
		return this;
	}

	@Override
	public ProductBuilder withAcronymLine(String acronymLine) {
		this.acronymLine = acronymLine;
		return this;
	}

	@Override
	public ProductBuilder withMultiple(int multiple) {
		this.multiple = multiple;
		return this;
	}

	@Override
	public ProductBuilder withCommercialBlock(boolean commercialBlock) {
		this.commercialBlock = commercialBlock;
		return this;
	}

	@Override
	public ProductBuilder withImage(byte[] image) {
		productImage.setImageStreams(image);
		return this;
	}

	@Override
	public ProductBuilder withUnitStValue(BigDecimal unitStValue) {
		this.unitStValue = unitStValue;
		return this;
	}

	@Override
	public ProductBuilder withUnitValue(BigDecimal unitValue) {
		this.unitValue = unitValue;
		return this;
	}

	@Override
	public ProductBuilder withUnitGrossValue(BigDecimal unitGrossValue) {
		this.unitGrossValue = unitGrossValue;
		return this;
	}

	@Override
	public ProductBuilder withDescription(String description) {
		this.description = description;
		return this;
	}

	@Override
	public ProductBuilder withTechDetail(ProductTechDetail t) {
		this.techDetail = t;
		return this;
	}

	@Override
	public ProductBuilder withQuantity(int quantity) {
		this.quantity = quantity;
		return this;
	}

	@Override
	public ContractProductBuilder withStock(int stock) {
		this.stock = stock;
		return this;
	}

	@Override
	public Product build() {
		return new Product(code, commercialCode, applicability, description, line, acronymLine, stock, commercialBlock,
				productImage, this.buildValue(), techDetail);
	}

	public ProductValue buildValue() {
		return new ProductValue(unitStValue, unitValue, unitGrossValue, quantity, multiple);
	}

}
