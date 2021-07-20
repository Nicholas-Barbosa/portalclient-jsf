package com.portal.client.dto;

import javax.validation.constraints.NotEmpty;

import com.portal.client.vo.CustomerOnOrder;

@NotEmpty
public class ProspectCustomerOnOrder extends CustomerOnOrder {

	private SellerType sellerType;

	public ProspectCustomerOnOrder() {
		// TODO Auto-generated constructor stub
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
