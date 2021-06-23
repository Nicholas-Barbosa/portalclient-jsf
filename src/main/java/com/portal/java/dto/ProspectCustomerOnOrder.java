package com.portal.java.dto;

public class ProspectCustomerOnOrder extends CustomerOnOrder {

	private SellerType sellerType;

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

	public void setState(String acronym) {
		Customer customer = new Customer(null, null, null, acronym, null, null, "PROSPECT", "PROSPECT", null, null);
		super.setCustomer(customer);
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
