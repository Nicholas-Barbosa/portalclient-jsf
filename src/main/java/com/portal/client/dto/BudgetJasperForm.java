package com.portal.client.dto;

import com.portal.client.export.jasper.BudgetJasperData;
import com.portal.client.security.user.RepresentativeUser.RepresentativeType;

public class BudgetJasperForm {

	private RepresentativeType sellertype;
	private BudgetJasperData data;

	public BudgetJasperForm(RepresentativeType sellertype, BudgetJasperData data) {
		super();
		this.sellertype = sellertype;
		this.data = data;
	}

	public RepresentativeType getSellertype() {
		return sellertype;
	}

	public BudgetJasperData getData() {
		return data;
	}

}
