package com.portal.java.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class ProspectCustomerForm {

	@NotEmpty
	private String sellerType;
	@NotEmpty
	private String stateAcronym;
	@NotEmpty
	private String address;
	@NotEmpty
	private String paymentTerms;
	
	@Size(max = 250)
	private String message;
	@NotEmpty
	private String cnpj;

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

	public void setAddress(String endereco) {
		this.address = endereco;
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
