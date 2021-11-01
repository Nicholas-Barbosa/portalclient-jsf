package com.portal.client.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class ProductPriceTabletWrapper {

	private ProductPriceTable[] list;

	@JsonbCreator
	public ProductPriceTabletWrapper(@JsonbProperty("table") ProductPriceTable[] list) {
		super();
		this.list = list;
	}

	public ProductPriceTable[] getList() {
		return list;
	}

	public static class ProductPriceTable implements Serializable{
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
				@JsonbProperty("unit_gross_value") BigDecimal unitGrossValue) {
			ProductValue value = new ProductValue(stValue, unitValue, unitGrossValue, 1, null);
			ProductProductPriceTable product = new ProductProductPriceTable(productCode, productCommercialCode, null,
					description, productLine, acronymLine, null, false, null, value, null, originPrice);
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
