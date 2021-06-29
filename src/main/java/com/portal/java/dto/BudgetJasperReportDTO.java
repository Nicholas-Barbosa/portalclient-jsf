package com.portal.java.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

public class BudgetJasperReportDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2698661307313799042L;

	private BigDecimal liquidValue;

	private BigDecimal grossValue;

	private BigDecimal stTotal;

	private CustomerJasperReportDTO customerReportDTO;

	private Set<BudgetItemJasperDTO> items;

	public BudgetJasperReportDTO(BigDecimal liquidValue, BigDecimal grossValue, BigDecimal stTotal,
			CustomerJasperReportDTO customerReportDTO, Set<BudgetItemJasperDTO> items) {
		super();
		this.liquidValue = liquidValue;
		this.grossValue = grossValue;
		this.stTotal = stTotal;
		this.customerReportDTO = customerReportDTO;
		this.items = new HashSet<>(items);
	}

	public BudgetJasperReportDTO(BudgetDTO budgetDTO) {
		this.liquidValue = budgetDTO.getLiquidValue();
		this.grossValue = budgetDTO.getGrossValue();
		this.stTotal = budgetDTO.getStValue();
		this.customerReportDTO = new CustomerJasperReportDTO(budgetDTO.getCustomerOnOrder().getCustomer());
		this.items = budgetDTO.getItems().parallelStream().map(BudgetItemJasperDTO::new)
				.collect(ConcurrentSkipListSet::new, Set::add, Set::addAll);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public BigDecimal getLiquidValue() {
		return liquidValue;
	}

	public BigDecimal getGrossValue() {
		return grossValue;
	}

	public BigDecimal getStTotal() {
		return stTotal;
	}

	public CustomerJasperReportDTO getCustomerReportDTO() {
		return customerReportDTO;
	}

	public Set<BudgetItemJasperDTO> getItems() {
		return items;
	}

	public static class CustomerJasperReportDTO implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = -3328263686333914653L;
		private String name;
		private String city;
		private String address;
		private String state;
		private String cgc;

		public CustomerJasperReportDTO(String name, String city, String address, String state, String cgc) {
			super();
			this.name = name;
			this.city = city;
			this.address = address;
			this.state = state;
			this.cgc = cgc;
		}

		public CustomerJasperReportDTO(Customer customer) {
			super();
			this.name = customer.getName();
			this.city = customer.getCity();
			this.address = customer.getAddress();
			this.state = customer.getState();
			this.cgc = customer.getCnpj();
		}

		public String getName() {
			return name;
		}

		public String getCity() {
			return city;
		}

		public String getAddress() {
			return address;
		}

		public String getState() {
			return state;
		}

		public String getCgc() {
			return cgc;
		}
	}

	public static class BudgetItemJasperDTO implements Comparable<BudgetItemJasperDTO> {

		private String commercialCode;
		private String line;
		private int quantity;
		private BigDecimal unitValue;
		private BigDecimal totalValue;
		private BigDecimal totalStValue;
		private BigDecimal discGlobal;
		private BigDecimal lineDisc;
		private BigDecimal totalGrossValue;
		private BigDecimal totalGrossValueWithoutDiscount;
		private final NumberFormat numberFormat = NumberFormat.getInstance(new Locale("pt", "BR"));

		public BudgetItemJasperDTO(String commercialCode, String line, int quantity, BigDecimal unitValue,
				BigDecimal totalValue, BigDecimal totalStValue, BigDecimal discGlobal, BigDecimal lineDisc,
				BigDecimal totalGrossValue, BigDecimal totalGrossValueWithoutDiscount) {
			super();
			this.commercialCode = commercialCode;
			this.line = line;
			this.quantity = quantity;
			this.unitValue = unitValue;
			this.totalValue = totalValue;
			this.totalStValue = totalStValue;
			this.discGlobal = discGlobal;
			this.lineDisc = lineDisc;
			this.totalGrossValue = totalGrossValue;
			this.totalGrossValueWithoutDiscount = totalGrossValueWithoutDiscount;
		}

		public BudgetItemJasperDTO(Item item) {
			super();
			this.commercialCode = item.getProduct().getCommercialCode();
			this.line = item.getProduct().getDescriptionType();
			this.quantity = item.getQuantity();

			ItemPrice prices = item.getItemPrice();
			this.unitValue = prices.getUnitValueWithoutDiscount();
			this.totalValue = this.unitValue.multiply(new BigDecimal(this.quantity));
			this.totalStValue = prices.getUnitStValueWithoutDiscount().multiply(new BigDecimal(this.quantity));
			this.discGlobal = item.getBudgetGlobalDiscount();
			this.totalGrossValue = prices.getTotalGrossValue();
			this.lineDisc = item.getLineDiscount();
			this.totalGrossValueWithoutDiscount = this.totalValue.add(this.totalStValue);

		}

		public String getCommercialCode() {
			return commercialCode;
		}

		public String getLine() {
			return line;
		}

		public int getQuantity() {
			return quantity;
		}

		public BigDecimal getUnitValue() {
			return unitValue;
		}

		public BigDecimal getTotalValue() {
			return totalValue;
		}

		public BigDecimal getTotalStValue() {
			return totalStValue;
		}

		public BigDecimal getDiscGlobal() {
			return discGlobal;
		}

		public BigDecimal getLineDisc() {
			return lineDisc;
		}

		public BigDecimal getTotalGrossValue() {
			return totalGrossValue;
		}

		public BigDecimal getTotalGrossValueWithoutDiscount() {
			return totalGrossValueWithoutDiscount;
		}

		@Override
		public int compareTo(BudgetItemJasperDTO o) {
			// TODO Auto-generated method stub
			return this.commercialCode.compareToIgnoreCase(o.commercialCode);
		}

	}
}
