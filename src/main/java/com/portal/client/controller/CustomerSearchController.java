package com.portal.client.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.data.PageEvent;

import com.portal.client.dto.Customer;
import com.portal.client.service.CustomerService;
import com.portal.client.ui.lazy.datamodel.CustomerLazyDataModel;
import com.portal.client.ui.lazy.datamodel.LazyBehaviorDataModel;
import com.portal.client.ui.lazy.datamodel.LazyPopulatorUtils;
import com.portal.client.util.jsf.FacesUtils;

@Named
@ViewScoped
public class CustomerSearchController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -813848448863455212L;
	private CustomerService customerService;
	private LazyBehaviorDataModel<Customer> customers;
	private String keyword;
	private int numberOfRows;

	@Inject
	public CustomerSearchController(CustomerService customerService) {
		super();
		this.customerService = customerService;
		this.customers = new CustomerLazyDataModel();
		this.numberOfRows = 6;
	}

	public void search(int page) {
		customerService.findByName(keyword, page, numberOfRows).ifPresentOrElse(c -> {
			LazyPopulatorUtils.populate(customers, c);
			FacesUtils.ajaxUpdate("dtCustomerResult");
			FacesUtils.executeScript("$('#noCustomersFound').hide();$('#content').show();");

		}, () -> {
			FacesUtils.executeScript("$('#noCustomersFound').show();$('#content').hide();");
		});
	}

	public void onPage(PageEvent pageEvent) {
		this.search(pageEvent.getPage() + 1);
	}

	public void onCustomerSelect(SelectEvent<Customer> event) {
		PrimeFaces.current().dialog().closeDynamic(event.getObject());
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public LazyBehaviorDataModel<Customer> getCustomers() {
		return customers;
	}

	public int getNumberOfRows() {
		return numberOfRows;
	}

}
