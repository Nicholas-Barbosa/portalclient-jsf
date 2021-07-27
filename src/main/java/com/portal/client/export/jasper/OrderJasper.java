package com.portal.client.export.jasper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import com.portal.client.dto.ItemBudget;
import com.portal.client.dto.ItemBudgetValue;
import com.portal.client.dto.BudgetToSave;
import com.portal.client.dto.Customer;
import com.portal.client.dto.CustomerOnOrder;

public class OrderJasper implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2698661307313799042L;

	private BigDecimal liquidValue;

	private BigDecimal grossValue;

	private BigDecimal stTotal;

	private CustomerJasperReportDTO customerReportDTO;

	private Set<OrderItemJasper> items;

	public OrderJasper(BigDecimal liquidValue, BigDecimal grossValue, BigDecimal stTotal,
			CustomerJasperReportDTO customerReportDTO, Set<OrderItemJasper> items) {
		super();
		this.liquidValue = liquidValue;
		this.grossValue = grossValue;
		this.stTotal = stTotal;
		this.customerReportDTO = customerReportDTO;
		this.items = new HashSet<>(items);
	}

	public OrderJasper(BudgetToSave budgetDTO) {
		this.liquidValue = budgetDTO.getLiquidValue();
		this.grossValue = budgetDTO.getGrossValue();
		this.stTotal = budgetDTO.getStValue();
		this.customerReportDTO = new CustomerJasperReportDTO(budgetDTO.getCustomerOnOrder());
		this.items = budgetDTO.getItems().parallelStream().map(OrderItemJasper::new).collect(ConcurrentSkipListSet::new,
				Set::add, Set::addAll);
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

	public Set<OrderItemJasper> getItems() {
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
		private String paymentTerms;
		private String message;

		public CustomerJasperReportDTO(String name, String city, String address, String state, String cgc,
				String paymentTerms, String message) {
			super();
			this.name = name;
			this.city = city;
			this.address = address;
			this.state = state;
			this.cgc = cgc;
			this.paymentTerms = paymentTerms;
			this.message = message;
		}

		public CustomerJasperReportDTO(CustomerOnOrder customer) {
			super();
			Customer originCustomer = customer.getCustomer();
			this.name = originCustomer.getName();
			this.city = originCustomer.getCity();
			this.address = originCustomer.getStreet();
			this.state = originCustomer.getState();
			this.cgc = originCustomer.getCnpj();
			this.paymentTerms = originCustomer.getPaymentTerms();
			this.message = customer.getMessage();
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

		public static long getSerialversionuid() {
			return serialVersionUID;
		}

		public String getPaymentTerms() {
			return paymentTerms;
		}

		public String getMessage() {
			return message;
		}

	}

	public static class OrderItemJasper implements Comparable<OrderItemJasper> {

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

		public OrderItemJasper(String commercialCode, String line, int quantity, BigDecimal unitValue,
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

		public OrderItemJasper(ItemBudget item) {
			super();
			this.commercialCode = item.getProduct().getCommercialCode();
			this.line = item.line();

			ItemBudgetValue values = item.getValues();
			this.quantity = values.getQuantity();
			this.unitValue = values.getUnitValueWithoutDiscount();
			this.totalValue = this.unitValue.multiply(new BigDecimal(this.quantity));
			this.totalStValue = values.getUnitStValueWithoutDiscount().multiply(new BigDecimal(this.quantity));
			this.discGlobal = values.getBudgetGlobalDiscount();
			this.totalGrossValue = values.getTotalGrossValue();
			this.lineDisc = values.getLineDiscount();
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
		public int compareTo(OrderItemJasper o) {
			// TODO Auto-generated method stub
			return this.commercialCode.compareToIgnoreCase(o.commercialCode);
		}

	}
}
