package com.portal.client.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

import com.portal.client.vo.Item;
import com.portal.client.vo.Product;
import com.portal.client.vo.ProductImage.ImageInfoState;
import com.portal.client.vo.builder.ProductImageBuilder;
import com.portal.client.vo.builder.ProductPriceBuilder;

public class ItemOrderFullProjection extends Item {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5778391426573612460L;

	@JsonbCreator
	public ItemOrderFullProjection(@JsonbProperty("st_value") BigDecimal stValue,
			@JsonbProperty("unit_gross_value") BigDecimal totalGrossValue,
			@JsonbProperty("unit_price") BigDecimal unitValue, @JsonbProperty("total_price") BigDecimal totalValue,
			@JsonbProperty("multiple") int multple, @JsonbProperty("description_product_type") String line,
			@JsonbProperty("commercial_code") String commercialCode, @JsonbProperty("product_code") String code,
			@JsonbProperty("description") String description, @JsonbProperty("product_type") String acronymLine,
			@JsonbProperty("quantity") int quantity) {
		super(new Product(code, commercialCode, description, line, acronymLine, null, null,
				ProductImageBuilder.getInstance().withState(ImageInfoState.NOT_LOADED).build(),
				ProductPriceBuilder.getInstance().withQuantity(1)
						.withUnitGrossValue(totalGrossValue.divide(BigDecimal.valueOf(quantity), RoundingMode.HALF_UP))
						.withUnitStValue(stValue).withUnitValue(unitValue).withTotalGrossValue(totalGrossValue)
						.withTotalStValue(stValue.multiply(BigDecimal.valueOf(quantity))).withTotalValue(totalValue)
						.build(),
				null));

	}

}
