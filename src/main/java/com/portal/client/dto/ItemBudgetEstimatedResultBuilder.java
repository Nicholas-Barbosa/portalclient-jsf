package com.portal.client.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class ItemBudgetEstimatedResultBuilder extends ItemBudgetJsonBuilder {

	private int stock;

	@JsonbCreator
	public ItemBudgetEstimatedResultBuilder(@JsonbProperty("product_code") String productCode,
			@JsonbProperty("commercial_code") String commercialCode,
			@JsonbProperty("unit_gross_value") BigDecimal unitGross,
			@JsonbProperty("total_gross_value") BigDecimal totalGross,
			@JsonbProperty("line_discount") BigDecimal lineDiscount, @JsonbProperty("unit_price") BigDecimal unitValue,
			@JsonbProperty("quantity") int quantity, @JsonbProperty("total_price") BigDecimal totalValue,
			@JsonbProperty("available_stock") int stock, @JsonbProperty("st_value") BigDecimal totalStValue) {
		this.withStock(stock).withCommercialCode(commercialCode).withProductCode(productCode)
				.withLineDiscount(lineDiscount).withQuantity(quantity).withTotalGrossValue(totalGross)
				.withUnitGrossValue(unitGross).withTotalStValue(totalStValue).withTotalValue(totalValue)
				.withUnitStValue(totalStValue.divide(new BigDecimal(quantity), RoundingMode.HALF_UP))
				.withUnitValue(unitValue).withGlobalDiscount(BigDecimal.ZERO);
	}

	public ItemBudgetEstimatedResultBuilder withStock(int stock) {
		this.stock = stock;
		return this;
	}

	public int getStock() {
		return stock;
	}

	@Override
	public ItemBudget build() {
		ProductValue productValue = new ProductValue(unitStValue, unitValue, unitGrossValue);
		Product product = new Product(productCode, commercialCode, null, null, null, null, 0, 0, false, null,
				productValue);
		ItemBudgetValue value = new ItemBudgetValue(quantity, budgetGlobalDiscount, lineDiscount, unitStValue,
				unitValue, unitGrossValue, totalStValue, totalValue, totalGrossValue, productValue);
		return new ItemBudget(product, value);
	}

}
