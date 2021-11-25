package com.portal.client.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

import com.portal.client.vo.Item;
import com.portal.client.vo.Product;
import com.portal.client.vo.ProductImage.ImageInfoState;
import com.portal.client.vo.builder.ProductImageBuilder;
import com.portal.client.vo.builder.ProductPriceBuilder;
import com.portal.client.vo.builder.ProductTechDetailBuilder;

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
		super(new Product(productCode, commercialCode, null, description, line, acronymLine, null, null,
				ProductImageBuilder.getInstance().withState(ImageInfoState.NOT_LOADED).build(),
				ProductPriceBuilder.getInstance()
						.withUnitStValue(stValue.divide(BigDecimal.valueOf(quantity), RoundingMode.HALF_UP))
						.withUnitValue(unitValue)
						.withUnitGrossValue(grossValue.divide(BigDecimal.valueOf(quantity), RoundingMode.HALF_UP))
						.withQuantity(quantity).withTotalGrossValue(grossValue).withTotalStValue(stValue)
						.withTotalValue(unitValue.multiply(BigDecimal.valueOf(quantity))).build(),
				application != null && !application.isBlank()
						? ProductTechDetailBuilder.getInstance().withApplication(application).build()
						: null));

	}

}
