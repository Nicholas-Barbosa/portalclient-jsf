package com.farawaybr.portal.vo;

import com.farawaybr.portal.security.user.ProtheusUser.SaleType;

public class ProspectCustomerOnOrder extends CustomerOnOrder {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3307367266965324272L;
	private SaleType sellerType;

	public ProspectCustomerOnOrder(String code, String store, String cnpj, String blocked, String name,
			String fantasyName, CustomerAddress address, CustomerPurchaseInfo financialInfo, CustomerContact contact,
			SaleType sellerType) {
		super(code, store, cnpj, blocked, name, fantasyName, address, financialInfo, contact);
		this.sellerType = sellerType;
		super.type = CustomerType.PROSPECT;
	}

	public SaleType getSellerType() {
		return sellerType;
	}

	public void setSellerType(SaleType sellerType) {
		this.sellerType = sellerType;
	}

}
