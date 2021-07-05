package com.portal.java.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class ProspectCustomerForm {

	@NotEmpty
	private String name;
	@NotEmpty
	private String sellerType;
	@NotEmpty
	private String stateAcronym;
	@NotEmpty
	private String address;
	@NotEmpty
	private String city;
	@NotEmpty
	private String district;
	@NotEmpty
	private String paymentTerms;
	@Size(max = 250)
	private String message;
	@NotEmpty
	private String cnpj;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSellerType() {
		return sellerType;
	}

	public void setSellerType(String sellerType) {
		this.sellerType = sellerType;
	}

	public String getStateAcronym() {
		return stateAcronym;
	}

	public void setStateAcronym(String stateAcronym) {
		this.stateAcronym = stateAcronym;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getPaymentTerms() {
		return paymentTerms;
	}

	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	
	

}
