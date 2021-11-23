package com.portal.client.dto;

import com.portal.client.security.user.RepresentativeUser.SaleType;

public class BudgetJasperForm {

	private SaleType sellertype;
	private BudgetJasperData data;

	public BudgetJasperForm(SaleType sellertype, BudgetJasperData data) {
		super();
		this.sellertype = sellertype;
		this.data = data;
	}

	public SaleType getSellertype() {
		return sellertype;
	}

	public BudgetJasperData getData() {
		return data;
	}

}
