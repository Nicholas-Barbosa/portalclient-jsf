package com.portal.client.controller.components;

import java.io.Serializable;
import java.util.stream.Collectors;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.data.PageEvent;

import com.portal.client.service.CustomerService;
import com.portal.client.ui.lazy.datamodel.CustomerLazyDataModel;
import com.portal.client.ui.lazy.datamodel.LazyBehaviorDataModel;
import com.portal.client.ui.lazy.datamodel.LazyPopulatorUtils;
import com.portal.client.util.jsf.FacesUtils;
import com.portal.client.vo.Customer;

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
	private Customer selectedCustomerToSeeDetails;
	private CustomerSearchObserver observer;

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
			this.selectedCustomerToSeeDetails = c.getContent().stream().limit(1).collect(Collectors.toList()).get(0);
			FacesUtils.ajaxUpdate("dtCustomerResult");
			FacesUtils.executeScript("$('#noCustomersFound').hide();$('#content').show();");

		}, () -> {
			FacesUtils.executeScript("$('#noCustomersFound').show();$('#content').hide();");
		});
	}

	public void onPage(PageEvent pageEvent) {
		this.search(pageEvent.getPage() + 1);
	}

	public void notifyObserver(SelectEvent<Customer> event) {
		if (!event.getObject().getBlocked().equals("Sim")) {
			System.out.println("Observer " + observer);
			this.selectedCustomerToSeeDetails = event.getObject();
			observer.onCustomerSelect(event.getObject());
			return;
		}
		FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().clear();
		FacesUtils.error(null, "Cliente bloqueado", "Este cliente não pode ser selecionado.", "growl");
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

	public Customer getSelectedCustomerToSeeDetails() {
		return selectedCustomerToSeeDetails;
	}

	public void setSelectedCustomerToSeeDetails(Customer selectedCustomerToSeeDetails) {
		this.selectedCustomerToSeeDetails = selectedCustomerToSeeDetails;
	}

	public CustomerSearchObserver getObserver() {
		return observer;
	}

	public void setObserver(CustomerSearchObserver observer) {
		this.observer = observer;
	}

}
