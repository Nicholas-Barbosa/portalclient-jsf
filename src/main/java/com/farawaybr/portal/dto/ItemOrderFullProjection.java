package com.farawaybr.portal.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

import com.farawaybr.portal.vo.Item;
import com.farawaybr.portal.vo.Product;
import com.farawaybr.portal.vo.ProductImage.ImageInfoState;
import com.farawaybr.portal.vo.builder.ProductImageBuilder;
import com.farawaybr.portal.vo.builder.ProductPriceBuilder;

public class ItemOrderFullProjection extends Item implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5778391426573612460L;

	@JsonbCreator
	public ItemOrderFullProjection(@JsonbProperty("st_value") BigDecimal totalStValue,
			@JsonbProperty("unit_gross_value") BigDecimal totalGrossValue,
			@JsonbProperty("unit_price") BigDecimal unitValue, @JsonbProperty("total_price") BigDecimal totalValue,
			@JsonbProperty("multiple") int multple, @JsonbProperty("description_product_type") String line,
			@JsonbProperty("commercial_code") String commercialCode, @JsonbProperty("product_code") String code,
			@JsonbProperty("description") String description, @JsonbProperty("product_type") String acronymLine,
			@JsonbProperty("quantity") int quantity, @JsonbProperty("client_order") String customerNumOrder) {
		super(new Product(code, commercialCode, null, description, line, acronymLine, null, null, null,
				ProductImageBuilder.getInstance().withState(ImageInfoState.NOT_LOADED).build(),
				ProductPriceBuilder.getInstance().withQuantity(quantity)
						.withUnitGrossValue(totalGrossValue.divide(BigDecimal.valueOf(quantity), RoundingMode.HALF_UP))
						.withUnitStValue(totalStValue.divide(BigDecimal.valueOf(quantity), RoundingMode.HALF_UP))
						.withUnitValue(unitValue).withTotalGrossValue(totalGrossValue).withTotalStValue(totalStValue)
						.withTotalValue(totalValue).withMultiple(multple).build(),
				null));
		super.setCustomerOrder(customerNumOrder);

	}

}
