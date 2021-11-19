package com.portal.client.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

import com.portal.client.vo.ProductPriceData;
import com.portal.client.vo.ProductTechDetail;
import com.portal.client.vo.builder.ProductPriceBuilder;

public class ProductPriceTableWrapper {

	private List<ProductPriceTable> list;

	@JsonbCreator
	public ProductPriceTableWrapper(@JsonbProperty("table") List<ProductPriceTable> list) {
		super();
		this.list = list;
	}

	public List<ProductPriceTable> getList() {
		return list;
	}

	public static class ProductPriceTable implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = -3094946114407381035L;
		private String code;
		private ProductProductPriceTable product;

		@JsonbCreator
		public ProductPriceTable(@JsonbProperty("table_cod") String code,
				@JsonbProperty("description_product_type") String productLine,
				@JsonbProperty("origin_price") BigDecimal originPrice,
				@JsonbProperty("product_type") String acronymLine, @JsonbProperty("product_code") String productCode,
				@JsonbProperty("commercial_code") String productCommercialCode,
				@JsonbProperty("unit_price") BigDecimal unitValue, @JsonbProperty("st_value") BigDecimal stValue,
				@JsonbProperty("description") String description,
				@JsonbProperty("unit_gross_value") BigDecimal unitGrossValue,
				@JsonbProperty("application") String application) {
			ProductPriceData priceData = ProductPriceBuilder.getInstance().withUnitStValue(stValue)
					.withUnitValue(unitValue).withQuantity(1).withUnitGrossValue(unitGrossValue)
					.withTotalGrossValue(unitGrossValue).withTotalStValue(stValue).withTotalValue(unitValue).build();
			ProductProductPriceTable product = new ProductProductPriceTable(productCode, productCommercialCode, null,
					description, productLine, acronymLine, null, false, null, priceData,
					new ProductTechDetail(null, application.isBlank() ? null : application, null, 0, null),
					originPrice);
			this.product = product;
			this.code = code;
		}

		public String getCode() {
			return code;
		}

		public ProductProductPriceTable getProduct() {
			return product;
		}
	}
}
