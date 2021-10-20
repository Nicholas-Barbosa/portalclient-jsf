package com.portal.client.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

import com.portal.client.vo.Item;
import com.portal.client.vo.ItemValue;
import com.portal.client.vo.Product;
import com.portal.client.vo.ProductImage;
import com.portal.client.vo.ProductImage.ImageInfoState;
import com.portal.client.vo.builder.ItemValueBuilder;
import com.portal.client.vo.builder.ProductBuilder;
import com.portal.client.vo.builder.ProductImageBuilder;

public class ItemBudgetProjection extends Item implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8717026150120991873L;

	public ItemBudgetProjection() {
		// TODO Auto-generated constructor stub
	}

	@JsonbCreator
	public static ItemBudgetProjection ofJsonb(@JsonbProperty("unit_gross_value") BigDecimal grossValue,
			@JsonbProperty("line_discount") BigDecimal lineDiscount,
			@JsonbProperty("commercial_code") String commercialCode, @JsonbProperty("product_code") String productCode,
			@JsonbProperty("unit_price") BigDecimal unitValue, @JsonbProperty("quantity") int quantity,
			@JsonbProperty("total_price") BigDecimal totalValue, @JsonbProperty("st_value") BigDecimal stValue,
			@JsonbProperty("description_product_type") String line, @JsonbProperty("product_type") String acronymLine,
			@JsonbProperty("multiple") int multiple, @JsonbProperty("description") String description) {
		BigDecimal quantityBgDecimal = new BigDecimal(quantity);
		ItemValue value = ItemValueBuilder.getInstance().withGlobalDiscount(BigDecimal.ZERO)
				.withLineDiscount(lineDiscount).withQuantity(quantity)
				.withUnitGrossValue(grossValue.divide(quantityBgDecimal, RoundingMode.HALF_UP))
				.withUnitStValue(stValue.divide(quantityBgDecimal,RoundingMode.HALF_UP)).withTotalValue(totalValue).withUnitValue(unitValue)
				.withTotalStValue(stValue).withMultiple(multiple).withTotalGrossValue(grossValue).build();

		ProductImage pImage = ProductImageBuilder.getInstance().withState(ImageInfoState.NOT_LOADED).build();
		Product product = ProductBuilder.getInstance().withUnitGrossValue(value.getUnitGrossValueWithoutDiscount())
				.withCommercialCode(commercialCode).withCode(productCode)
				.withUnitValue(value.getUnitValueWithoutDiscount()).withQuantity(1)
				.withUnitStValue(value.getUnitStValueWithoutDiscount()).withCommercialCode(commercialCode)
				.withCode(productCode).withAcronymLine(acronymLine).withLine(line).withDescription(description)
				.withImage(pImage).build();
		return new ItemBudgetProjection(product, value);

	}

	public ItemBudgetProjection(Product product, ItemValue value) {
		super(product, value);
	}

}
