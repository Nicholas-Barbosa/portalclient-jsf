package com.farawaybr.portal.dto;

import java.math.BigDecimal;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

import com.farawaybr.portal.vo.Product;
import com.farawaybr.portal.vo.ProductDiscountData;
import com.farawaybr.portal.vo.ProductPriceData;
import com.farawaybr.portal.vo.builder.ProductDiscountDataBuilder;
import com.farawaybr.portal.vo.builder.ProductTechDetailBuilder;

public class ProductSearchDto {

	private Product product;

	public ProductSearchDto(Product product) {
		super();
		this.product = product;
	}

	@JsonbCreator
	public static ProductSearchDto ofJsonb(@JsonbProperty("application") String application,
			@JsonbProperty("code") String code, @JsonbProperty("description_product_type") String line,
			@JsonbProperty("product_type") String acronymLine, @JsonbProperty("multiple") int multiple,
			@JsonbProperty("commercial_block") String commercialBlock, @JsonbProperty("commercial_code") String cCode,
			@JsonbProperty("st_value") BigDecimal stValue, @JsonbProperty("unit_price") BigDecimal unitValue,
			@JsonbProperty("stock") int stock, @JsonbProperty("description") String description,
			@JsonbProperty("unit_gross_value") BigDecimal unitGrossValue, @JsonbProperty("ncm") String ncm,
			@JsonbProperty("quantity_on_order") Integer quantityOnOrders) {
		ProductDiscountData discountData = ProductDiscountDataBuilder.getInstance().withUnitGrossValue(unitGrossValue)
				.withUnitStValue(stValue).withUnitValue(unitValue).withTotalValue(unitValue)
				.withTotalGrossValue(unitGrossValue).withTotalStValue(stValue).build();
		ProductPriceData price = new ProductPriceData(stValue, unitValue, unitGrossValue, 1, multiple, discountData);
		return new ProductSearchDto(new Product(code, cCode, ncm, description, line, acronymLine, stock,
				quantityOnOrders,
				commercialBlock == null ? null : commercialBlock.equalsIgnoreCase("nao") ? false : true, null, price,
				application != null && !application.isBlank()
						? ProductTechDetailBuilder.getInstance().withApplication(application).build()
						: null));
	}

	public Product getProduct() {
		return product;
	}
}
