package com.portal.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class FinancialTitlePageDTO extends BasePageDTO {

	private Set<FinacialTitleDTO> titles;

	@JsonbCreator
	public FinancialTitlePageDTO(@JsonbProperty("total_items") int totalItems,
			@JsonbProperty("total_page") int totalPage, @JsonbProperty("page_size") int pageSize, int page,
			Set<FinacialTitleDTO> titles) {
		super(page, pageSize, totalItems, totalPage);
		this.titles = titles;
	}

	public Set<FinacialTitleDTO> getTitles() {
		return new HashSet<>(titles);
	}

	public static class FinacialTitleDTO {
		private BigDecimal sale;
		private String docNumber;
		private String customerName;
		private LocalDateTime date;
		private String situation;

		@JsonbCreator
		public FinacialTitleDTO(BigDecimal sale, @JsonbProperty("doc_number") String docNumber,
				@JsonbProperty("client_name") String customerName, @JsonbProperty("due_date") LocalDateTime date,
				String situation) {
			super();
			this.sale = sale;
			this.docNumber = docNumber;
			this.customerName = customerName;
			this.date = date;
			this.situation = situation;
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

		public LocalDateTime getDate() {
			return date;
		}

		public String getSituation() {
			return situation;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((date == null) ? 0 : date.hashCode());
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
			if (date == null) {
				if (other.date != null)
					return false;
			} else if (!date.equals(other.date))
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
