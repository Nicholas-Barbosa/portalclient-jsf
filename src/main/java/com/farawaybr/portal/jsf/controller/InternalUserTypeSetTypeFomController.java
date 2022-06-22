package com.farawaybr.portal.jsf.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.farawaybr.portal.security.api.helper.ProtheusAPIHelper;
import com.farawaybr.portal.security.user.InternalProtheusUser;
import com.farawaybr.portal.security.user.ProtheusUser.SaleType;
import com.farawaybr.portal.util.jsf.FacesUtils;

@RequestScoped
@Named
public class InternalUserTypeSetTypeFomController {

	private SaleType type;

	@Inject
	private ProtheusAPIHelper protheusApi;

	public void saveSaleType() {
		InternalProtheusUser user = (InternalProtheusUser) protheusApi.getUser();
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
