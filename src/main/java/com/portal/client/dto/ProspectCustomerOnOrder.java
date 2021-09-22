package com.portal.client.dto;

import com.portal.client.security.user.RepresentativeUser.RepresentativeType;

public class ProspectCustomerOnOrder extends CustomerOnOrder {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3307367266965324272L;
	private RepresentativeType sellerType;

	
	public ProspectCustomerOnOrder(String code, String store, String cnpj, String blocked, String name,
			String fantasyName, CustomerAddress address, CustomerPurchaseInfo financialInfo, CustomerContact contact,
			RepresentativeType sellerType) {
		super(code, store, cnpj, blocked, name, fantasyName, address, financialInfo, contact);
		this.sellerType = sellerType;
		super.type = CustomerType.PROSPECT;
	}

	public RepresentativeType getSellerType() {
		return sellerType;
	}

	public void setSellerType(RepresentativeType sellerType) {
		this.sellerType = sellerType;
	}

	
}
