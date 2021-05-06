package com.portal.dto;

import java.math.BigDecimal;
import java.util.List;

import javax.json.bind.annotation.JsonbProperty;

public class ResponseQuoteBudgetDTO {

	@JsonbProperty("client_code")
	private String customerCode;

	@JsonbProperty("liquid_order_value")
	private BigDecimal liquidValue;

	@JsonbProperty("gross_order_value")
	private BigDecimal grossValue;

	@JsonbProperty("client_description")
	private String customer;

	@JsonbProperty("estimate")
	private List<ResponseQuoteEstimatedValue> estimatedValues;

	public String getCustomerCode() {
		return customerCode;
	}

	public BigDecimal getLiquidValue() {
		return liquidValue;
	}

	public BigDecimal getGrossValue() {
		return grossValue;
	}

	public String getCustomer() {
		return customer;
	}

	public List<ResponseQuoteEstimatedValue> getEstimatedValues() {
		return estimatedValues;
	}

	
	@Override
	public String toString() {
		return "ResponseQuoteBudgetDTO [customerCode=" + customerCode + ", liquidValue=" + liquidValue + ", grossValue="
				+ grossValue + ", customer=" + customer + "]";
	}


	public static class ResponseQuoteEstimatedValue {

		@JsonbProperty("unit_gross_value")
		private BigDecimal unitGrossValue;

		@JsonbProperty("total_gross_value")
		private BigDecimal totalGrossValue;

		@JsonbProperty("product_code")
		private String productCode;

		@JsonbProperty("commercial_code")
		private String commercialCode;

		@JsonbProperty("unit_price")
		private BigDecimal unitPrice;

		@JsonbProperty("quantity")
		private Integer quantity;

		@JsonbProperty("total_price")
		private BigDecimal totale;

		@JsonbProperty("st_value")
		private BigDecimal stValue;

		public BigDecimal getUnitGrossValue() {
			return unitGrossValue;
		}

		public BigDecimal getTotalGrossValue() {
			return totalGrossValue;
		}

		public String getProductCode() {
			return productCode;
		}

		public String getCommercialCode() {
			return commercialCode;
		}

		public BigDecimal getUnitPrice() {
			return unitPrice;
		}

		public Integer getQuantity() {
			return quantity;
		}

		public BigDecimal getTotale() {
			return totale;
		}

		public BigDecimal getStValue() {
			return stValue;
		}

	}

}
