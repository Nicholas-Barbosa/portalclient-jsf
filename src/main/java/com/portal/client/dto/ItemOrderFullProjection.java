package com.portal.client.dto;

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

public class ItemOrderFullProjection extends Item {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5778391426573612460L;

	@JsonbCreator
	public static ItemOrderFullProjection ofJsonb(@JsonbProperty("st_value") BigDecimal stValue,
			@JsonbProperty("unit_gross_value") BigDecimal totalGrossValue,
			@JsonbProperty("unit_price") BigDecimal unitValue, @JsonbProperty("total_price") BigDecimal totalValue,
			@JsonbProperty("multiple") int multple, @JsonbProperty("description_product_type") String line,
			@JsonbProperty("commercial_code") String commercialCode, @JsonbProperty("product_code") String code,
			@JsonbProperty("description") String description, @JsonbProperty("product_type") String acronymLine,
			@JsonbProperty("quantity") int quantity) {
		ItemValue value = ItemValueBuilder.getInstance().withMultiple(multple).withQuantity(quantity)
				.withTotalGrossValue(totalGrossValue).withUnitStValue(stValue).withUnitValue(unitValue)
				.withTotalValue(totalValue)
				.withUnitGrossValue(totalGrossValue.divide(new BigDecimal(quantity), RoundingMode.HALF_UP)).build();

		ProductImage image = ProductImageBuilder.getInstance().withState(ImageInfoState.NOT_LOADED).build();

		Product product = ProductBuilder.getInstance().withAcronymLine(acronymLine).withCode(code)
				.withCommercialCode(commercialCode).withDescription(description).withLine(line).withQuantity(1)
				.withUnitGrossValue(value.getUnitGrossValueWithoutDiscount())
				.withUnitStValue(value.getUnitStValueWithoutDiscount())
				.withUnitValue(value.getUnitValueWithoutDiscount()).withImage(image).build();
		return new ItemOrderFullProjection(product, value);

	}

	public ItemOrderFullProjection(Product product, ItemValue value) {
		super(product, value);
		// TODO Auto-generated constructor stub
	}

}
