package com.portal.client.dto;

public class ProspectCustomerOnOrder extends CustomerOnOrder {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3307367266965324272L;
	private SellerType sellerType;

	public ProspectCustomerOnOrder(Customer customer,SellerType selletType) {
		super(customer, CustomerType.PROSPECT);
	}

	public SellerType getSellerType() {
		return sellerType;
	}

	public void setSellerType(SellerType sellerType) {
		this.sellerType = sellerType;
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
