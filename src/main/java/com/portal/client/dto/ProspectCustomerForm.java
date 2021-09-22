package com.portal.client.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.portal.client.security.user.RepresentativeUser.RepresentativeType;

public class ProspectCustomerForm {

	@NotEmpty
	private String name;
	private RepresentativeType sellerType;
	private String stateAcronym;
	@NotEmpty
	private String street;
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
	@NotEmpty
	private String zipCode;

	public ProspectCustomerOnOrder toProsp() {
		CustomerAddress address = new CustomerAddress(street, district, city, zipCode, stateAcronym);
		CustomerPurchaseInfo purschase = new CustomerPurchaseInfo(0, 0, 0, null, null, paymentTerms, null, null);
		return new ProspectCustomerOnOrder(null, null, cnpj, "Nao", name, name, address, purschase, null, sellerType);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RepresentativeType getSellerType() {
		return sellerType;
	}

	public void setSellerType(RepresentativeType sellerType) {
		this.sellerType = sellerType;
	}

	public String getStateAcronym() {
		return stateAcronym;
	}

	public void setStateAcronym(String stateAcronym) {
		this.stateAcronym = stateAcronym;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String address) {
		this.street = address;
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

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Override
	public String toString() {
		return "ProspectCustomerForm [name=" + name + ", sellerType=" + sellerType + ", stateAcronym=" + stateAcronym
				+ ", street=" + street + ", city=" + city + ", district=" + district + ", paymentTerms=" + paymentTerms
				+ ", message=" + message + ", cnpj=" + cnpj + "]";
	}

}
