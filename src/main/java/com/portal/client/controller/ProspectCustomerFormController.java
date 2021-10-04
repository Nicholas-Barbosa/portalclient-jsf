package com.portal.client.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.portal.client.dto.ProspectCustomerForm;
import com.portal.client.service.OrderCommonBehaviorHelper;
import com.portal.client.service.ZipCodeService;
import com.portal.client.util.jsf.FacesUtils;
import com.portal.client.vo.Order;

@Named
@ViewScoped
public class ProspectCustomerFormController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2041869205618143200L;

	@Inject
	private ZipCodeService zipCodeService;

	@Inject
	private OrderCommonBehaviorHelper orderHelper;

	private ProspectCustomerForm prospectCustomerForm;

	private String cepToSearch;

	public ProspectCustomerFormController() {
		prospectCustomerForm = new ProspectCustomerForm();
	}

	public void findCep() {
		zipCodeService.find(cepToSearch).ifPresentOrElse(zipCode -> {
			this.prospectCustomerForm.setStreet(zipCode.getStreet());
			this.prospectCustomerForm.setStateAcronym(zipCode.getState());
			this.prospectCustomerForm.setDistrict(zipCode.getDistrict());
			this.prospectCustomerForm.setCity(zipCode.getCity());
			this.prospectCustomerForm.setZipCode(cepToSearch);
			FacesUtils.info(null, "CEP encontrado!", zipCode.getStreet() + ", " + zipCode.getDistrict() + " - "
					+ zipCode.getCity() + " lat: " + zipCode.getLat() + " lng: " + zipCode.getLng());
		}, () -> {
			FacesUtils.error(null, "CEP não encontrado", "Digite os dados do endereço manualmente.", "growl");
		});
	}

	public void create(Order order) {
		orderHelper.setCustomer(order, prospectCustomerForm.toProsp());
	}

	public ProspectCustomerForm getProspectCustomerForm() {
		return prospectCustomerForm;
	}

	public String getCepToSearch() {
		return cepToSearch;
	}

	public void setCepToSearch(String cepToSearch) {
		this.cepToSearch = cepToSearch;
	}
}
