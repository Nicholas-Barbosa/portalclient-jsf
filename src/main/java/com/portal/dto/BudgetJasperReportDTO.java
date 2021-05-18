package com.portal.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.bouncycastle.asn1.cmc.GetCert;

import com.portal.dto.BudgetEstimateDTO.EstimatedItem;

public class BudgetJasperReportDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2698661307313799042L;

	private BigDecimal liquidValue;

	private BigDecimal grossValue;

	private BigDecimal stTotal;

	private CustomerJasperReportDTO customerReportDTO;

	private List<EstimatedItem> items;

	public BudgetJasperReportDTO(BigDecimal liquidValue, BigDecimal grossValue, BigDecimal stTotal,
			CustomerJasperReportDTO customerReportDTO, List<EstimatedItem> items) {
		super();
		this.liquidValue = liquidValue;
		this.grossValue = grossValue;
		this.stTotal = stTotal;
		this.customerReportDTO = customerReportDTO;
		this.items = new ArrayList<>(items);
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

	public List<EstimatedItem> getItems() {
		return new ArrayList<>(items);
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

}
