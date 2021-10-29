package com.portal.client.dto;

import java.math.BigDecimal;

import com.portal.client.vo.Product;
import com.portal.client.vo.ProductImage;
import com.portal.client.vo.ProductTechDetail;

public class ProductProductPriceList extends Product {

	private BigDecimal originPrice;

	public ProductProductPriceList(String code, String commercialCode, String applicability, String description, String line,
			String acronymLine, Integer stock, boolean commercialBlock, ProductImage image, ProductValue price,
			ProductTechDetail productTechDetail, BigDecimal originPrice) {
		super(code, commercialCode, applicability, description, line, acronymLine, stock, commercialBlock, image, price,
				productTechDetail);
		this.originPrice = originPrice;
	}

	public BigDecimal getOriginPrice() {
		return originPrice;
	}
}
