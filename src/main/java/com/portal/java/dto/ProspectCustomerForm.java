package com.portal.java.dto;

import javax.validation.constraints.NotEmpty;

@NotEmpty
public class ProspectCustomerForm {

	private String sellerType;
	private String stateAcronym;

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

}
