package com.portal.client.resources.export.jasper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import com.portal.client.dto.CustomerOnOrder;
import com.portal.client.vo.Item;
import com.portal.client.vo.Order;
import com.portal.client.vo.Product;
import com.portal.client.vo.ProductPriceData;

public class BudgetJasperData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2698661307313799042L;

	private BigDecimal liquidValue;

	private BigDecimal grossValue;

	private BigDecimal stTotal;

	private CustomerJasperReportDTO customerReportDTO;

	private Set<BudgetItemJasperData> items;

	private String message;

	private String representative;

	public BudgetJasperData(BigDecimal liquidValue, BigDecimal grossValue, BigDecimal stTotal,
			CustomerJasperReportDTO customerReportDTO, Set<BudgetItemJasperData> items, String message,
			String representative) {
		super();
		this.liquidValue = liquidValue;
		this.grossValue = grossValue;
		this.stTotal = stTotal;
		this.customerReportDTO = customerReportDTO;
		this.items = new HashSet<>(items);
		this.message = message;
		this.representative = representative;
	}

	public BudgetJasperData(Order order) {
		this.liquidValue = order.getLiquidValue();
		this.grossValue = order.getGrossValue();
		this.stTotal = order.getStValue();
		this.customerReportDTO = new CustomerJasperReportDTO(order.getCustomerOnOrder());
		this.items = order.getItems().parallelStream().map(BudgetItemJasperData::new)
				.collect(ConcurrentSkipListSet::new, Set::add, Set::addAll);
		this.message = order.getMessage();

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

	public Set<BudgetItemJasperData> getItems() {
		return items;
	}

	public String getMessage() {
		return message;
	}

	public String getRepresentative() {
		return representative;
	}

	public void setRepresentative(String representative) {
		this.representative = representative;
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

		public CustomerJasperReportDTO(String name, String city, String address, String state, String cgc,
				String paymentTerms) {
			super();
			this.name = name;
			this.city = city;
			this.address = address;
			this.state = state;
			this.cgc = cgc;
			this.paymentTerms = paymentTerms;
		}

		public CustomerJasperReportDTO(CustomerOnOrder customer) {
			super();
			this.name = customer.getName();
			this.city = customer.getCity();
			this.address = customer.getStreet();
			this.state = customer.getState();
			this.cgc = customer.getCnpj();
			this.paymentTerms = customer.getFinancialInfo().getPaymentTerms();
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

	}

	public static class BudgetItemJasperData implements Comparable<BudgetItemJasperData> {

		private String commercialCode;
		private String description;
		private int quantity;
		private BigDecimal unitValue;
		private BigDecimal totalValue;
		private BigDecimal totalStValue;
		private float lineDisc;
		private BigDecimal totalGrossValue;
		private BigDecimal totalGrossValueWithoutDiscount;

		public BudgetItemJasperData(String commercialCode, String description, int quantity, BigDecimal unitValue,
				BigDecimal totalValue, BigDecimal totalStValue, float lineDisc, BigDecimal totalGrossValue,
				BigDecimal totalGrossValueWithoutDiscount) {
			super();
			this.commercialCode = commercialCode;
			this.description = description;
			this.quantity = quantity;
			this.unitValue = unitValue;
			this.totalValue = totalValue;
			this.totalStValue = totalStValue;
			this.lineDisc = lineDisc;
			this.totalGrossValue = totalGrossValue;
			this.totalGrossValueWithoutDiscount = totalGrossValueWithoutDiscount;

		}

		public BudgetItemJasperData(Item item) {
			super();
			Product product = item.getProduct();
			ProductPriceData priceData = product.getPriceData();

			this.commercialCode = product.getCommercialCode();
			this.description = product.getDescription();
			this.quantity = priceData.getQuantity();
			this.unitValue = priceData.getUnitValue();
			this.totalValue = priceData.getTotalValue();
			this.totalStValue = priceData.getTotalStValue();
			this.totalGrossValue = priceData.getTotalGrossValue();
			this.lineDisc = priceData.getDiscountData().getDiscount();
			this.totalGrossValueWithoutDiscount = priceData.getTotalGrossValue();

		}

		public String getCommercialCode() {
			return commercialCode;
		}

		public String getDescription() {
			return description;
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

		public float getLineDisc() {
			return lineDisc;
		}

		public BigDecimal getTotalGrossValue() {
			return totalGrossValue;
		}

		public BigDecimal getTotalGrossValueWithoutDiscount() {
			return totalGrossValueWithoutDiscount;
		}

		@Override
		public int compareTo(BudgetItemJasperData o) {
			// TODO Auto-generated method stub
			return this.commercialCode.compareToIgnoreCase(o.commercialCode);
		}

	}
}
