package com.portal.client.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class ItemBudgetEstimatedResultBuilder extends ItemBudgetJsonBuilder {

	@JsonbCreator
	public ItemBudgetEstimatedResultBuilder(@JsonbProperty("product_code") String productCode,
			@JsonbProperty("commercial_code") String commercialCode,
			@JsonbProperty("unit_gross_value") BigDecimal unitGross,
			@JsonbProperty("total_gross_value") BigDecimal totalGross,
			@JsonbProperty("line_discount") BigDecimal lineDiscount, @JsonbProperty("unit_price") BigDecimal unitValue,
			@JsonbProperty("quantity") int quantity, @JsonbProperty("total_price") BigDecimal totalValue,
			@JsonbProperty("available_stock") int stock, @JsonbProperty("st_value") BigDecimal totalStValue,
			@JsonbProperty("description") String description, @JsonbProperty("multiple") int multiple,
			@JsonbProperty("product_type") String acronymLine) {
		Product product = ProductBuilder.getInstance().withCode(productCode).withCommercialCode(commercialCode)
				.withUnitGrossValue(unitGross).withUnitValue(unitValue)
				.withUnitStValue(totalStValue.divide(new BigDecimal(quantity), RoundingMode.HALF_UP))
				.withDescription(description).withAcronymLine(acronymLine).withMultiple(multiple).build();
		super.withGlobalDiscount(BigDecimal.ZERO).withTotalGrossValue(totalGross).withLineDiscount(lineDiscount)
				.withQuantity(quantity).withTotalValue(totalValue).withTotalStValue(totalStValue).withProduct(product);
	}

	@Override
	public ItemBudget build() {

		ItemBudgetValue value = new ItemBudgetValue(super.getQuantity(), super.getBudgetGlobalDiscount(),
				super.getLineDiscount(), super.getProduct().getValue().getUnitStValue(),
				super.getProduct().getValue().getUnitValue(), super.getProduct().getValue().getUnitGrossValue(),
				super.getTotalStValue(), super.getTotalValue(), super.getTotalGrossValue(),
				super.getProduct().getValue());
		return new ItemBudget(super.getProduct(), value);
	}

}
