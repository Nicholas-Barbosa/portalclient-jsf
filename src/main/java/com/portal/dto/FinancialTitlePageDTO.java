package com.portal.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

import com.portal.dto.FinancialTitlePageDTO.FinacialTitleDTO;

public class FinancialTitlePageDTO extends BasePageDTO<FinacialTitleDTO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 926845547155848707L;
	private Set<FinacialTitleDTO> titles;

	@JsonbCreator
	public FinancialTitlePageDTO(@JsonbProperty("total_items") int totalItems,
			@JsonbProperty("total_page") int totalPage, @JsonbProperty("page_size") int pageSize,
			@JsonbProperty("page") int page, @JsonbProperty("titles") Set<FinacialTitleDTO> titles) {
		super(page, pageSize, totalItems, totalPage);
		this.titles = titles;
	}

	public Set<FinacialTitleDTO> getTitles() {
		return new HashSet<>(titles);
	}

	@Override
	public Collection<FinacialTitleDTO> getContent() {
		// TODO Auto-generated method stub
		return this.getTitles();
	}

	public static class FinacialTitleDTO {
		private BigDecimal sale;
		private String docNumber;
		private String customerName;
		private LocalDateTime dueDate;
		private String situation;

		@JsonbCreator
		public FinacialTitleDTO(@JsonbProperty("sale") BigDecimal sale, @JsonbProperty("doc_number") String docNumber,
				@JsonbProperty("client_name") String customerName, @JsonbProperty("due_date") String date,
				@JsonbProperty("situation") String situation) {
			super();
			this.sale = sale;
			this.docNumber = docNumber;
			this.customerName = customerName;
			this.situation = situation;
			parseToDate(date);
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

		public LocalDateTime getDueDate() {
			return dueDate;
		}

		public String getSituation() {
			return situation;
		}

		private void parseToDate(String date) {
			int lastIndexOf = date.lastIndexOf("-");
			this.dueDate = LocalDateTime.parse(date.substring(0, lastIndexOf),
					DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
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
			FinacialTitleDTO other = (FinacialTitleDTO) obj;
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
