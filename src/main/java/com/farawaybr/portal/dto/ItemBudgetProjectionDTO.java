package com.farawaybr.portal.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

import com.farawaybr.portal.vo.Item;
import com.farawaybr.portal.vo.Product;
import com.farawaybr.portal.vo.ProductTechDetail;
import com.farawaybr.portal.vo.ProductImage.ImageInfoState;
import com.farawaybr.portal.vo.builder.ProductImageBuilder;
import com.farawaybr.portal.vo.builder.ProductPriceBuilder;
import com.farawaybr.portal.vo.builder.ProductTechDetailBuilder;

public class ItemBudgetProjectionDTO extends Item implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8717026150120991873L;

	@JsonbCreator
	public ItemBudgetProjectionDTO(@JsonbProperty("unit_gross_value") BigDecimal grossValue,
			@JsonbProperty("line_discount") BigDecimal lineDiscount,
			@JsonbProperty("commercial_code") String commercialCode, @JsonbProperty("product_code") String productCode,
			@JsonbProperty("unit_price") BigDecimal unitValue, @JsonbProperty("quantity") int quantity,
			@JsonbProperty("total_price") BigDecimal totalValue, @JsonbProperty("st_value") BigDecimal stValue,
			@JsonbProperty("description_product_type") String line, @JsonbProperty("product_type") String acronymLine,
			@JsonbProperty("multiple") int multiple, @JsonbProperty("description") String description,
			@JsonbProperty("application") String application) {
		super(new Product(productCode, commercialCode, null, description, line, acronymLine, null, null, null,
				ProductImageBuilder.getInstance().withState(ImageInfoState.NOT_LOADED).build(),
				ProductPriceBuilder.getInstance()
						.withUnitStValue(stValue.equals(BigDecimal.ZERO) ? BigDecimal.ZERO
								: stValue.divide(BigDecimal.valueOf(quantity), RoundingMode.HALF_UP))
						.withUnitValue(unitValue)
						.withUnitGrossValue(grossValue.equals(BigDecimal.ZERO) ? BigDecimal.ZERO
								: grossValue.divide(BigDecimal.valueOf(quantity), RoundingMode.HALF_UP))
						.withQuantity(quantity).withTotalGrossValue(grossValue).withTotalStValue(stValue)
						.withTotalValue(unitValue.multiply(BigDecimal.valueOf(quantity))).withMultiple(multiple)
						.build(),
				application != null && !application.isBlank()
						? ProductTechDetailBuilder.getInstance().withApplication(application).build()
						: ProductTechDetailBuilder.getInstance().build()));

	}

}
