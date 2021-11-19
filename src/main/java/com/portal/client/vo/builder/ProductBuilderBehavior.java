package com.portal.client.vo.builder;

import java.math.BigDecimal;

import com.portal.client.vo.Product;
import com.portal.client.vo.ProductDiscountData;
import com.portal.client.vo.ProductImage;
import com.portal.client.vo.ProductTechDetail;

public interface ProductBuilderBehavior {
	ProductBuilderBehavior withCode(String code);

	ProductBuilderBehavior withCommercialCode(String commercialCode);

	ProductBuilderBehavior withApplicability(String applicability);

	ProductBuilderBehavior withLine(String line);

	ProductBuilderBehavior withAcronymLine(String acronymLine);

	ProductBuilderBehavior withMultiple(int multiple);

	ProductBuilderBehavior withCommercialBlock(boolean commercialBlock);

	ProductBuilderBehavior withImage(ProductImage image);

	ProductBuilderBehavior withUnitStValue(BigDecimal unitStValue);

	ProductBuilderBehavior withUnitValue(BigDecimal unitValue);

	ProductBuilderBehavior withUnitGrossValue(BigDecimal unitGrossValue);

	ProductBuilderBehavior withDescription(String description);

	ProductBuilderBehavior withTechDetail(ProductTechDetail t);

	ProductBuilderBehavior withQuantity(int quantity);

	ProductBuilderBehavior withStock(int stock);

	ProductBuilderBehavior withDiscountData(ProductDiscountData discount);
	
	Product build();
}
