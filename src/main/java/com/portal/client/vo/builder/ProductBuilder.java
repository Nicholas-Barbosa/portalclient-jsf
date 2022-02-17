package com.portal.client.vo.builder;

import java.math.BigDecimal;

import com.portal.client.vo.Product;
import com.portal.client.vo.ProductDiscountData;
import com.portal.client.vo.ProductImage;
import com.portal.client.vo.ProductPriceData;
import com.portal.client.vo.ProductTechDetail;

public class ProductBuilder {

	protected String code, commercialCode, ncm, description, line, acronymLine;
	protected int quantity;
	protected boolean commercialBlock;
	protected ProductImage productImage;
	protected ProductTechDetail techDetail;
	protected Integer stock, multiple, quantityOnOrders;
	protected BigDecimal unitStValue, unitValue, unitGrossValue;
	protected ProductDiscountData discountData;

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

	public ProductBuilder withImage(ProductImage image) {
		this.productImage = image;
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

	public ProductBuilder withQuantity(int quantity) {
		this.quantity = quantity;
		return this;
	}

	public ProductBuilder withStock(int stock) {
		this.stock = stock;
		return this;
	}

	public ProductBuilder withDiscountData(ProductDiscountData discount) {
		this.discountData = discount;
		return this;
	}

	public Product build() {
		return new Product(code, commercialCode, ncm, description, line, acronymLine, stock, quantityOnOrders,
				commercialBlock, productImage, this.buildValue(), techDetail);
	}

	public ProductPriceData buildValue() {
		return new ProductPriceData(unitStValue, unitValue, unitGrossValue, quantity, multiple, discountData);
	}

	public ProductBuilder withNcm(String ncm) {
		this.ncm = ncm;
		return this;
	}

}
