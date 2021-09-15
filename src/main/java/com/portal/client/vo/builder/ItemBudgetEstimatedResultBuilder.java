package com.portal.client.vo.builder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

import com.portal.client.vo.Item;
import com.portal.client.vo.ItemValue;
import com.portal.client.vo.Product;

public class ItemBudgetEstimatedResultBuilder extends ItemBuilder {

	@JsonbCreator
	public ItemBudgetEstimatedResultBuilder(@JsonbProperty("product_code") String productCode,
			@JsonbProperty("commercial_code") String commercialCode,
			@JsonbProperty("unit_gross_value") BigDecimal unitGross,
			@JsonbProperty("total_gross_value") BigDecimal totalGross,
			@JsonbProperty("line_discount") BigDecimal lineDiscount, @JsonbProperty("unit_price") BigDecimal unitValue,
			@JsonbProperty("quantity") int quantity, @JsonbProperty("total_price") BigDecimal totalValue,
			@JsonbProperty("available_stock") int stock, @JsonbProperty("st_value") BigDecimal totalStValue,
			@JsonbProperty("description") String description, @JsonbProperty("multiple") int multiple,
			@JsonbProperty("product_type") String acronymLine, @JsonbProperty("description_product_type") String line) {
		System.out.println("Unit value " + unitGross);
		Product product = ProductBuilder.getInstance().withCode(productCode).withCommercialCode(commercialCode)
				.withUnitGrossValue(unitGross).withUnitValue(unitValue)
				.withUnitStValue(totalStValue.divide(new BigDecimal(quantity), RoundingMode.HALF_UP))
				.withDescription(description).withAcronymLine(acronymLine).withMultiple(multiple).withLine(line)
				.withQuantity(quantity).withStock(stock).build();
		ItemValue itemValue = ItemValueBuilder.getInstance().withGlobalDiscount(BigDecimal.ZERO)
				.withTotalGrossValue(totalGross).withLineDiscount(lineDiscount).withQuantity(quantity)
				.withTotalValue(totalValue).withTotalStValue(totalStValue).withProductValue(product.getValue()).build();
		super.withProduct(product).withValue(itemValue);

	}

	@Override
	public Item build() {
		return super.build();
	}

	public static List<Item> build(List<ItemBudgetEstimatedResultBuilder> items) {
		return items.stream().map(ItemBudgetEstimatedResultBuilder::build).collect(Collectors.toList());

	}
}
