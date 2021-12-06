package com.portal.client.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.portal.client.security.api.helper.ProtheusAPIHelper;
import com.portal.client.security.user.InternalRepresentativeUser;
import com.portal.client.security.user.RepresentativeUser.SaleType;
import com.portal.client.util.jsf.FacesUtils;

@RequestScoped
@Named
public class InternalUserTypeSetTypeFomController {

	private SaleType type;

	@Inject
	private ProtheusAPIHelper protheusApi;

	public void saveSaleType() {
		InternalRepresentativeUser user = (InternalRepresentativeUser) protheusApi.getUser();
		user.setLoggedSaleType(type);
		FacesUtils.info(null, "Tipo salvo com sucesso", null, "growl");
	}

	public SaleType getType() {
		return type;
	}

	public void setType(SaleType type) {
		this.type = type;
	}
}
