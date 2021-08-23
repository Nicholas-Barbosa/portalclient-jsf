package com.portal.client.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.portal.client.dto.Customer;
import com.portal.client.service.CustomerService;
import com.portal.client.ui.lazy.datamodel.LazyBehaviorDataModel;
import com.portal.client.util.jsf.FacesUtils;

@Named
@RequestScoped
public class CustomerSearchController {

	private CustomerService customerService;
	private LazyBehaviorDataModel<Customer>customers;
	private String nameToSearch;

	@Inject
	public CustomerSearchController(CustomerService customerService) {
		super();
		this.customerService = customerService;
	}

	public void search() {
		customerService.findByName(nameToSearch, 1, 10).ifPresentOrElse(c -> {
			FacesUtils.info(null, "Clientes encontrados", c.totalItems() + " resultados correspondentes", "growl");
		}, () -> {
			FacesUtils.error(null, "Nenhum cliente encontrado!", "Tente por outro nome", "growl");
		});
	}

	public String getNameToSearch() {
		return nameToSearch;
	}

	public void setNameToSearch(String nameToSearch) {
		this.nameToSearch = nameToSearch;
	}

	public LazyBehaviorDataModel<Customer> getCustomers() {
		return customers;
	}
}
