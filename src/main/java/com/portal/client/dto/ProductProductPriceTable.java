package com.portal.client.dto;

import java.math.BigDecimal;

import com.portal.client.vo.Product;
import com.portal.client.vo.ProductImage;
import com.portal.client.vo.ProductPriceData;
import com.portal.client.vo.ProductTechDetail;

public class ProductProductPriceTable extends Product {

	private BigDecimal originPrice;

	public ProductProductPriceTable(String code, String commercialCode, String applicability, String description, String line,
			String acronymLine, Integer stock, boolean commercialBlock, ProductImage image, ProductPriceData price,
			ProductTechDetail productTechDetail, BigDecimal originPrice) {
		super(code, commercialCode, description, line, acronymLine, stock, commercialBlock, image, price,
				productTechDetail);
		this.originPrice = originPrice;
	}

	public BigDecimal getOriginPrice() {
		return originPrice;
	}
}
