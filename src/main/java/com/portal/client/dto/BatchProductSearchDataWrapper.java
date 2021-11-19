package com.portal.client.dto;

import java.math.BigDecimal;
import java.util.Set;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

import com.portal.client.vo.Product;

public class BatchProductSearchDataWrapper {

	private BigDecimal liquidValue, grossValue;
	private Set<BatchProductData> products;

	@JsonbCreator
	public BatchProductSearchDataWrapper(@JsonbProperty("liquid_order_value") BigDecimal liquidValue,
			@JsonbProperty("gross_order_value") BigDecimal grossValue,
			@JsonbProperty("client_code") String customerCode,
			@JsonbProperty("estimate") Set<BatchProductData> products) {
		this.liquidValue = liquidValue;
		this.grossValue = grossValue;
		this.products = products;
	}

	public BigDecimal getLiquidValue() {
		return liquidValue;
	}

	public BigDecimal getGrossValue() {
		return grossValue;
	}

	public Set<BatchProductData> getProducts() {
		return products;
	}

	@Override
	public String toString() {
		return "BatchProductSearchDataWrapper [liquidValue=" + liquidValue + ", grossValue=" + grossValue
				+ ", products=" + products + "]";
	}

	public static class BatchProductData {

		private Product product;

		@JsonbCreator
		public BatchProductData(@JsonbProperty("product_code") String productCode,
				@JsonbProperty("commercial_code") String commercialCode,
				@JsonbProperty("unit_gross_value") BigDecimal unitGross,
				@JsonbProperty("total_gross_value") BigDecimal totalGross,
				@JsonbProperty("line_discount") BigDecimal lineDiscount,
				@JsonbProperty("unit_price") BigDecimal unitValue, @JsonbProperty("quantity") int quantity,
				@JsonbProperty("total_price") BigDecimal totalValue, @JsonbProperty("available_stock") int stock,
				@JsonbProperty("st_value") BigDecimal totalStValue, @JsonbProperty("description") String description,
				@JsonbProperty("multiple") int multiple, @JsonbProperty("product_type") String acronymLine,
				@JsonbProperty("description_product_type") String line) {
			this.product = Product.ofJsonb(null, productCode, line, acronymLine, multiple, null, commercialCode,
					totalStValue, unitValue, stock, description, unitGross);

		}

		public Product getProduct() {
			return product;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((product == null) ? 0 : product.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			BatchProductData other = (BatchProductData) obj;
			if (product == null) {
				if (other.product != null)
					return false;
			} else if (!product.equals(other.product))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "BatchProductData [product=" + product + "]";
		}

	}

}
