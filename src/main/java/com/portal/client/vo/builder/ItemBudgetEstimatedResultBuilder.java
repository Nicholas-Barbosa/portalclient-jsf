package com.portal.client.vo.builder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;
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
		try {
			Product product = ProductBuilder.getInstance().withCode(productCode).withCommercialCode(commercialCode)
					.withUnitGrossValue(unitGross).withUnitValue(unitValue)
					.withUnitStValue(totalStValue.divide(new BigDecimal(quantity), RoundingMode.HALF_UP))
					.withDescription(description).withAcronymLine(acronymLine).withMultiple(multiple).withLine(line)
					.withQuantity(quantity).withStock(stock).build();
			super.withGlobalDiscount(BigDecimal.ZERO).withTotalGrossValue(totalGross).withLineDiscount(lineDiscount)
					.withQuantity(quantity).withTotalValue(totalValue).withTotalStValue(totalStValue)
					.withProduct(product);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Item build() {
		ItemValue value = new ItemValue(super.getQuantity(), super.getProduct().getValue().getMultiple(),
				super.getBudgetGlobalDiscount(), super.getLineDiscount(),
				super.getProduct().getValue().getUnitStValue(), super.getProduct().getValue().getUnitValue(),
				super.getProduct().getValue().getUnitGrossValue(), super.getTotalStValue(), super.getTotalValue(),
				super.getTotalGrossValue()

		);
		return new Item(super.getProduct(), value);
	}

	public static Set<Item> build(Set<ItemBudgetEstimatedResultBuilder> items) {
		return items.stream().map(ItemBudgetEstimatedResultBuilder::build).collect(Collectors.toSet());

	}
}
