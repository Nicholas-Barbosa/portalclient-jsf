package com.portal.client.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

import com.portal.client.dto.FinancialBondsPage.FinacialBondsDTO;
import com.portal.client.dto.helper.StringToDateParser;

public class FinancialBondsPage extends BasePageDTO<FinacialBondsDTO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 926845547155848707L;
	private Set<FinacialBondsDTO> financialBonds;

	@JsonbCreator
	public FinancialBondsPage(@JsonbProperty("total_items") int totalItems,
			@JsonbProperty("total_page") int totalPage, @JsonbProperty("page_size") int pageSize,
			@JsonbProperty("page") int page, @JsonbProperty("titles") Set<FinacialBondsDTO> titles) {
		super(page, pageSize, totalItems, totalPage);
		this.financialBonds = titles;
	}

	public Set<FinacialBondsDTO> getFinancialBonds() {
		return new HashSet<>(financialBonds);
	}

	@Override
	public Collection<FinacialBondsDTO> getContent() {
		// TODO Auto-generated method stub
		return this.getFinancialBonds();
	}

	public static class FinacialBondsDTO {
		private BigDecimal sale;
		private String docNumber;
		private String customerName;
		private LocalDate dueDate;
		private String situation;

		@JsonbCreator
		public FinacialBondsDTO(@JsonbProperty("sale") BigDecimal sale, @JsonbProperty("doc_number") String docNumber,
				@JsonbProperty("client_name") String customerName, @JsonbProperty("due_date") String date,
				@JsonbProperty("situation") String situation) {
			super();
			this.sale = sale;
			this.docNumber = docNumber;
			this.customerName = customerName;
			this.situation = situation;
			this.dueDate =  StringToDateParser.convert(date);
		}

		public BigDecimal getSale() {
			return sale;
		}

		public String getDocNumber() {
			return docNumber;
		}

		public String getCustomerName() {
			return customerName;
		}

		public LocalDate getDueDate() {
			return dueDate;
		}

		public String getSituation() {
			return situation;
		}

	

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((dueDate == null) ? 0 : dueDate.hashCode());
			result = prime * result + ((docNumber == null) ? 0 : docNumber.hashCode());
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
			FinacialBondsDTO other = (FinacialBondsDTO) obj;
			if (dueDate == null) {
				if (other.dueDate != null)
					return false;
			} else if (!dueDate.equals(other.dueDate))
				return false;
			if (docNumber == null) {
				if (other.docNumber != null)
					return false;
			} else if (!docNumber.equals(other.docNumber))
				return false;
			return true;
		}

	}
}
