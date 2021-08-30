package com.portal.client.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

import com.portal.client.vo.ItemBudget;
import com.portal.client.vo.ItemValue;
import com.portal.client.vo.Product;

public class ItemBudgetProjection extends ItemBudget {

	@JsonbCreator
	public static ItemBudgetProjection ofJsonb(@JsonbProperty("unit_gross_value") BigDecimal grossValue,
			@JsonbProperty("line_discount") BigDecimal lineDiscount,
			@JsonbProperty("commercial_code") String commercialCode, @JsonbProperty("product_code") String productCode,
			@JsonbProperty("unit_price") BigDecimal unitValue, @JsonbProperty("quantity") int quantity,
			@JsonbProperty("total_price") BigDecimal totalValue, @JsonbProperty("st_value") BigDecimal stValue) {
		BigDecimal quantityBgDecimal = new BigDecimal(quantity);

		Product product = createProduct(stValue, unitValue, grossValue, lineDiscount, quantityBgDecimal, productCode,
				commercialCode);

		ItemValue value = new ItemValue(quantity, null, BigDecimal.ZERO, lineDiscount,
				stValue.divide(quantityBgDecimal, RoundingMode.HALF_UP), unitValue,
				grossValue.divide(quantityBgDecimal, RoundingMode.HALF_UP), stValue, totalValue, grossValue);

		return new ItemBudgetProjection(product, value);

	}

	public ItemBudgetProjection(Product product, ItemValue value) {
		super(product, value);

	}

	private static Product createProduct(BigDecimal rawStValue, BigDecimal rawUnitValue, BigDecimal rawGrossValue,
			BigDecimal lineDiscount, BigDecimal quantity, String code, String commercialCode) {
//		BigDecimal unitStValue = MathUtils.addValueByPercentage(lineDiscount,
//				rawStValue.divide(quantity, RoundingMode.HALF_UP));
//		BigDecimal unitValue = MathUtils.addValueByPercentage(lineDiscount, rawUnitValue);
//		BigDecimal unitGrossValue = MathUtils.addValueByPercentage(lineDiscount,
//				rawGrossValue.divide(quantity, RoundingMode.HALF_UP));
//
//		ProductValue productValue = new ProductValue(unitStValue, unitValue, unitGrossValue);
//
//		Product product = new Product(code, commercialCode, null, null, null, null, 0, 0, false, null, productValue,
//				null);
		return null;
	}

}
