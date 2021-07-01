package com.portal.java.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NotEmpty
public class ProspectCustomerOnOrder extends CustomerOnOrder {

	private SellerType sellerType;

	@Size(max = 250)
	private String message;

	public ProspectCustomerOnOrder() {
		// TODO Auto-generated constructor stub
	}

	public ProspectCustomerOnOrder(Customer customer, CustomerType type) {
		super(customer, type);
		// TODO Auto-generated constructor stub
	}

	public SellerType getSellerType() {
		return sellerType;
	}

	public void setSellerType(SellerType sellerType) {
		this.sellerType = sellerType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static enum SellerType {
		CARROS("C"), MOTOS("M"), AGRICOLA("A");

		private final String type;

		private SellerType(String type) {
			this.type = type;
		}

		public String getType() {
			return type;
		}

	}
}
