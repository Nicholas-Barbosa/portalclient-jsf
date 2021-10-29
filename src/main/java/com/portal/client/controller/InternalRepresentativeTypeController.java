package com.portal.client.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import com.portal.client.cdi.event.InternalUserEventPayload;
import com.portal.client.security.api.helper.APIHelper;
import com.portal.client.security.user.InternalRepresentativeUser;
import com.portal.client.security.user.RepresentativeUser;
import com.portal.client.security.user.RepresentativeUser.SaleType;
import com.portal.client.util.jsf.FacesUtils;

@SessionScoped
@Named
public class InternalRepresentativeTypeController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6272442544543060253L;

	private boolean isInternal;

	@Inject
	private APIHelper protheusApi;

	private SaleType sellerType;

	private InternalRepresentativeUser user;

	private boolean typeSaved;

//	public void showDialog() {
//		System.out.println("showDialog " + typeSaved + " isInternal " + isInternal + " user" + this.user);
//		if (!typeSaved && isInternal) {
//			if (this.user != null) {
//				System.out.println("Show dialog, user != null " + user);
//				FacesUtils.executeScript("PF('dlgSaleType').show()");
//			}
//		}
//	}

	public void save() {
		System.out.println("sale type " + sellerType + " user " + this.user);
		this.user.setType(sellerType);
		this.typeSaved = true;

	}


	public void setUser(@Observes InternalRepresentativeUser user) {
		this.user = user;
	}

	public void setSellerType(SaleType sellerType) {
		this.sellerType = sellerType;
	}

	public SaleType getSellerType() {
		return sellerType;
	}

}
