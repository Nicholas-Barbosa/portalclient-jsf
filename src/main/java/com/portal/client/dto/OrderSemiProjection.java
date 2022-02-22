package com.portal.client.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class OrderSemiProjection extends OrderPersisted {

	private String customerCode, customerStore, customerName, invoice44Digits, invoiceNumber, invoiceSerie, status,
			customerShortname;
	private LocalDate createdAt, invoiceCreatedAt;

	@JsonbCreator
	public OrderSemiProjection(@JsonbProperty("order_code") String code,
			@JsonbProperty("client_code") String customerCode, @JsonbProperty("store") String customerStore,
			@JsonbProperty("cliente_name") String customerName, @JsonbProperty("creation_date") String createdAt,
			@JsonbProperty("invoice_key") String invoiceDigits, @JsonbProperty("invoice_number") String invoiceNumber,
			@JsonbProperty("invoice_series") String invoiceSerie,
			@JsonbProperty("issuance_date") String invoiceCreatedAt, @JsonbProperty("status") String status,
			@JsonbProperty("client_short_name") String customerShortname) {
		super(code);
		this.customerCode = customerCode;
		this.customerStore = customerStore;
		this.customerName = customerName;
		this.createdAt = convertToDate(createdAt);
		this.invoice44Digits = invoiceDigits == null ? null : invoiceDigits.isBlank() ? null : invoiceDigits;
		this.invoiceNumber = invoiceNumber;
		this.invoiceSerie = invoiceSerie;
		this.invoiceCreatedAt = invoiceCreatedAt != null ? convertToDate(createdAt) : null;
		this.status = status;
		this.customerShortname = customerShortname;
	}

	private final LocalDate convertToDate(String str) {
		return LocalDate.parse(str.substring(0, str.lastIndexOf("T")), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public String getCustomerStore() {
		return customerStore;
	}

	public String getCustomerName() {
		return customerName;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public String getInvoice44Digits() {
		return invoice44Digits;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public String getInvoiceSerie() {
		return invoiceSerie;
	}

	public LocalDate getInvoiceCreatedAt() {
		return invoiceCreatedAt;
	}

	public String getStatus() {
		return status;
	}

	public String getCustomerShortname() {
		return customerShortname;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((super.getCode() == null) ? 0 : super.getCode().hashCode());
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
		OrderSemiProjection other = (OrderSemiProjection) obj;
		if (customerStore == null) {
			if (other.getCode() != null)
				return false;
		} else if (!customerStore.equals(other.getCode()))
			return false;
		return true;
	}

}
