package com.portal.client.dto;

import java.math.BigDecimal;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

import com.portal.client.vo.Item;
import com.portal.client.vo.ItemValue;
import com.portal.client.vo.Product;
import com.portal.client.vo.builder.ItemValueBuilder;
import com.portal.client.vo.builder.ProductBuilder;

public class ItemBudgetProjection extends Item {

	@JsonbCreator
	public static ItemBudgetProjection ofJsonb(@JsonbProperty("unit_gross_value") BigDecimal grossValue,
			@JsonbProperty("line_discount") BigDecimal lineDiscount,
			@JsonbProperty("commercial_code") String commercialCode, @JsonbProperty("product_code") String productCode,
			@JsonbProperty("unit_price") BigDecimal unitValue, @JsonbProperty("quantity") int quantity,
			@JsonbProperty("total_price") BigDecimal totalValue, @JsonbProperty("st_value") BigDecimal stValue) {
		BigDecimal quantityBgDecimal = new BigDecimal(quantity);

		ItemValue value = ItemValueBuilder.getInstance().withGlobalDiscount(BigDecimal.ZERO)
				.withLineDiscount(lineDiscount).withQuantity(quantity).withUnitGrossValue(grossValue)
				.withUnitStValue(stValue.divide(quantityBgDecimal)).withTotalValue(totalValue).withUnitValue(unitValue)
				.build();

		Product product = ProductBuilder.getInstance().withUnitGrossValue(value.getUnitGrossValueWithoutDiscount())
				.withCommercialCode(commercialCode).withCode(productCode)
				.withUnitValue(value.getUnitValueWithoutDiscount()).withQuantity(1)
				.withUnitStValue(value.getUnitStValueWithoutDiscount()).withCommercialCode(commercialCode)
				.withCode(productCode).build();

		return new ItemBudgetProjection(product, value);

	}

	public ItemBudgetProjection(Product product, ItemValue value) {
		super(product, value);
	}

}
