package com.portal.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.json.bind.annotation.JsonbProperty;

public class BudgetEstimateDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4987730225653316397L;

	@JsonbProperty("client_code")
	private String customerCode;

	@JsonbProperty("liquid_order_value")
	private BigDecimal liquidValue;

	@JsonbProperty("gross_order_value")
	private BigDecimal grossValue;

	@JsonbProperty("client_description")
	private String customer;

	@JsonbProperty("estimate")
	private List<EstimatedValueDTO> estimatedValues;

	private BigDecimal stTotal;

	public BudgetEstimateDTO() {

	}

	public BudgetEstimateDTO(String customerCode, BigDecimal liquidValue, BigDecimal grossValue, String customer,
			List<EstimatedValueDTO> estimatedValues, BigDecimal stTotal) {
		super();
		this.customerCode = customerCode;
		this.liquidValue = liquidValue;
		this.grossValue = grossValue;
		this.customer = customer;
		this.estimatedValues = estimatedValues;
		this.stTotal = stTotal;
	}

	public void reCalculateTotales() {
		this.liquidValue = new BigDecimal(estimatedValues.parallelStream().map(e -> e.totale)
				.collect(Collectors.summingDouble(v -> v.doubleValue())));
		this.grossValue = new BigDecimal(estimatedValues.parallelStream().map(e -> e.totalGrossValue)
				.collect(Collectors.summingDouble(v -> v.doubleValue())));
		setStTotal();
	}

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

	public List<EstimatedValueDTO> getEstimatedValues() {
		return estimatedValues;
	}

	public BigDecimal getStTotal() {
		return stTotal;
	}

	public void setStTotal() {
		stTotal = new BigDecimal(estimatedValues.stream().mapToLong(e -> e.getStValue().longValue()).sum());
	}

	@Override
	public String toString() {
		return "ResponseQuoteBudgetDTO [customerCode=" + customerCode + ", liquidValue=" + liquidValue + ", grossValue="
				+ grossValue + ", customer=" + customer + "]";
	}

	public static class EstimatedValueDTO {

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
		private int quantity;

		@JsonbProperty("total_price")
		private BigDecimal totale;

		@JsonbProperty("st_value")
		private BigDecimal stValue;

		private BigDecimal unitStValue;

		public EstimatedValueDTO() {
			// TODO Auto-generated constructor stub
		}

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

		public int getQuantity() {
			return quantity;
		}

		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}

		public BigDecimal getTotale() {
			return totale;
		}

		public BigDecimal getStValue() {
			return stValue;
		}

		public void setStValue(BigDecimal stValue) {
			this.stValue = stValue;
			discoverUnitStValue();
		}

		public BigDecimal getUnitStValue() {
			return unitStValue;
		}

		public void recalculateTotales() {
			calculateTotaleStValue();
			calculateTotalGrossValue();
			calculateTotalPrice();
		}

		public final void calculateTotalGrossValue() {
			this.totalGrossValue = unitGrossValue.multiply(new BigDecimal(quantity));
		}

		public final void calculateTotalPrice() {
			this.totale = unitPrice.multiply(new BigDecimal(quantity));
		}

		public final void discoverUnitStValue() {
			this.unitStValue = stValue.divide(new BigDecimal(quantity));
		}

		public final void calculateTotaleStValue() {
			if (unitStValue != null) {
				this.stValue = unitStValue.multiply(new BigDecimal(quantity));
			} else
				throw new IllegalStateException("You must set unitStValue before call this method.");
		}

	}

}
