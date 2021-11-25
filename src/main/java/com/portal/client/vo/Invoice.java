package com.portal.client.vo;

import java.time.LocalDate;

public class Invoice {

	private String digits44, number, serie;
	private LocalDate createdAt;

	public Invoice() {
		// TODO Auto-generated constructor stub
	}

	public Invoice(String digits44, String number, String serie, LocalDate createdAt) {
		super();
		this.digits44 = digits44;
		this.number = number;
		this.serie = serie;
		this.createdAt = createdAt;
	}

	public String getDigits44() {
		return digits44;
	}

	public String getNumber() {
		return number;
	}

	public String getSerie() {
		return serie;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

}
