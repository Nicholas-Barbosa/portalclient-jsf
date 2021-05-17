package com.portal.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class BudgetEstimateDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4987730225653316397L;

	private BigDecimal liquidValue;

	private BigDecimal grossValue;

	private List<EstimatedItem> estimatedItemValues;

	private BigDecimal stTotal;

	private CustomerDTO customer;

	public BudgetEstimateDTO() {

	}

	public BudgetEstimateDTO(BigDecimal liquidValue, BigDecimal grossValue, List<EstimatedItem> estimatedValues,
			BigDecimal stTotal, CustomerDTO customer) {
		super();
		this.liquidValue = liquidValue;
		this.grossValue = grossValue;
		this.estimatedItemValues = new ArrayList<>(estimatedValues);
		this.stTotal = stTotal;
		this.customer = customer == null ? new CustomerDTO() : new CustomerDTO(customer);
	}

	@JsonbCreator
	public BudgetEstimateDTO(@JsonbProperty("liquid_order_value") BigDecimal liquidValue,
			@JsonbProperty("gross_order_value") BigDecimal grossValue,
			@JsonbProperty("estimate") List<EstimatedItem> estimatedValues) {
		super();
		this.liquidValue = liquidValue;
		this.grossValue = grossValue;
		this.estimatedItemValues = estimatedValues;
		setStTotal();

	}

	public BudgetEstimateDTO(BudgetEstimateDTO estimate) {
		this(estimate.liquidValue, estimate.grossValue, estimate.estimatedItemValues, estimate.stTotal,
				estimate.customer);
	}

	public BudgetEstimateDTO(CustomerDTO customer) {
		this.customer = customer;
	}

	public void reCalculateTotales() {
		this.liquidValue = new BigDecimal(estimatedItemValues.parallelStream().map(e -> e.totale)
				.collect(Collectors.summingDouble(v -> v.doubleValue())));
		this.grossValue = new BigDecimal(estimatedItemValues.parallelStream().map(e -> e.totalGrossValue)
				.collect(Collectors.summingDouble(v -> v.doubleValue())));
		setStTotal();
	}

	public BigDecimal getLiquidValue() {
		return liquidValue;
	}

	public BigDecimal getGrossValue() {
		return grossValue;
	}

	public List<EstimatedItem> getEstimatedItemValues() {
		return estimatedItemValues;
	}

	public BigDecimal getStTotal() {
		return stTotal;
	}

	public void setStTotal() {
		stTotal = new BigDecimal(estimatedItemValues.stream().mapToLong(e -> e.getStValue().longValue()).sum());
	}

	public CustomerDTO getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerDTO customer) {
		this.customer = new CustomerDTO(customer);

	}

	@Override
	public String toString() {
		return "BudgetEstimateDTO [liquidValue=" + liquidValue + ", grossValue=" + grossValue + ", estimatedValues="
				+ estimatedItemValues + ", stTotal=" + stTotal + ", customerDTO=" + customer + "]";
	}

	public static class EstimatedItem implements Serializable{

		

		/**
		 * 
		 */
		private static final long serialVersionUID = -8265751772406946002L;

		private BigDecimal unitGrossValue;

		private BigDecimal totalGrossValue;

		private String productCode;

		private String commercialCode;

		private BigDecimal unitPrice;

		private int quantity;

		private BigDecimal totale;

		private BigDecimal stValue;

		private BigDecimal unitStValue;

		public EstimatedItem() {
			// TODO Auto-generated constructor stub
		}

		@JsonbCreator
		public EstimatedItem(@JsonbProperty("unit_gross_value") BigDecimal unitGrossValue,
				@JsonbProperty("total_gross_value") BigDecimal totalGrossValue,
				@JsonbProperty("product_code") String productCode,
				@JsonbProperty("commercial_code") String commercialCode,
				@JsonbProperty("unit_price") BigDecimal unitPrice, @JsonbProperty("quantity") int quantity,
				@JsonbProperty("total_price") BigDecimal totale, @JsonbProperty("st_value") BigDecimal stValue) {
			super();
			this.unitGrossValue = unitGrossValue;
			this.totalGrossValue = totalGrossValue;
			this.productCode = productCode;
			this.commercialCode = commercialCode;
			this.unitPrice = unitPrice;
			this.quantity = quantity;
			this.totale = totale;
			this.stValue = stValue;
			discoverUnitStValue();
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

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((commercialCode == null) ? 0 : commercialCode.hashCode());
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
			EstimatedItem other = (EstimatedItem) obj;
			if (commercialCode == null) {
				if (other.commercialCode != null)
					return false;
			} else if (!commercialCode.equals(other.commercialCode))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "EstimatedItem [unitGrossValue=" + unitGrossValue + ", totalGrossValue=" + totalGrossValue
					+ ", productCode=" + productCode + ", commercialCode=" + commercialCode + ", unitPrice=" + unitPrice
					+ ", quantity=" + quantity + ", totale=" + totale + ", stValue=" + stValue + ", unitStValue="
					+ unitStValue + "]";
		}
		
		

	}

}
