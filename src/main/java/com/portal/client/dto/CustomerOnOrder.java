package com.portal.client.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class CustomerOnOrder extends Customer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4894289566624375884L;
	private CustomerType type;
	@Size(max = 250)
	private String message;

	public CustomerOnOrder(@NotEmpty Customer customer, @NotEmpty CustomerType type) {
		super(customer);
		this.type = type;
	}

	public CustomerOnOrder(@NotEmpty Customer customer, @NotEmpty CustomerType type, @Size(max = 250) String message) {
		super(customer);
		this.type = type;
		this.message = message;
	}

	public CustomerOnOrder(String code, String store, String cnpj, String blocked, String name, String fantasyName,
			CustomerAddress address, CustomerPurchaseInfo financialInfo, CustomerContact contact) {
		super(code, store, cnpj, blocked, name, fantasyName, address, financialInfo, contact);
	}

	public CustomerType getType() {
		return type;
	}

	public void setType(CustomerType type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static enum CustomerType {
		NORMAL, PROSPECT
	}

}
