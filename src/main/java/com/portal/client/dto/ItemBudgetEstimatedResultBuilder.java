package com.portal.client.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class ItemBudgetEstimatedResultBuilder extends ItemBudgetJsonBuilder {

	private int stock;

	@JsonbCreator
	public static ItemBudgetEstimatedResultBuilder getInstance(@JsonbProperty("product_code") String productCode,
			@JsonbProperty("commercial_code") String commercialCode,
			@JsonbProperty("unit_gross_value") BigDecimal unitGross,
			@JsonbProperty("total_gross_value") BigDecimal totalGross,
			@JsonbProperty("line_discount") BigDecimal lineDiscount, @JsonbProperty("unit_price") BigDecimal unitValue,
			@JsonbProperty("quantity") int quantity, @JsonbProperty("total_price") BigDecimal totalValue,
			@JsonbProperty("available_stock") int stock, @JsonbProperty("st_value") BigDecimal totalStValue) {
		ItemBudgetEstimatedResultBuilder resultBuilder = new ItemBudgetEstimatedResultBuilder();
		resultBuilder.withStock(stock).withCommercialCode(commercialCode).withProductCode(productCode)
				.withLineDiscount(lineDiscount).withQuantity(quantity).withTotalGrossValue(totalGross)
				.withUnitGrossValue(unitGross).withTotalStValue(totalStValue).withTotalValue(totalValue)
				.withUnitStValue(totalStValue.divide(new BigDecimal(quantity), RoundingMode.HALF_UP))
				.withUnitValue(unitValue);
		return resultBuilder;
	}

	public ItemBudgetEstimatedResultBuilder withStock(int stock) {
		this.stock = stock;
		return this;
	}

	public int getStock() {
		return stock;
	}
}
