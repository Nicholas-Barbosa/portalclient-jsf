package com.portal.client.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.ProcessingException;

import com.portal.client.controller.show.CustomerDetailShowController;
import com.portal.client.dto.Customer;
import com.portal.client.dto.CustomerOnOrder;
import com.portal.client.dto.CustomerUtils;
import com.portal.client.dto.SearchCustomerByCodeAndStoreDTO;
import com.portal.client.service.CustomerService;
import com.portal.client.util.jsf.FacesUtils;
import com.portal.client.util.jsf.ProcessingExceptionFacesMessageHelper;
import com.portal.client.vo.Order;

@RequestScoped
@Named
public class CustomerOnOrderDataViewerController {

	private Order order;
	private CustomerDetailShowController customerShow;
	private CustomerService customerService;
	private ProcessingExceptionFacesMessageHelper processingMsgHelper;

	@Inject
	public CustomerOnOrderDataViewerController(CustomerDetailShowController customerShow,
			CustomerService customerService, ProcessingExceptionFacesMessageHelper processingMsgHelper) {
		super();
		this.customerShow = customerShow;
		this.customerService = customerService;
		this.processingMsgHelper = processingMsgHelper;
	}

	public void view(Order order, String componentId) {
		this.order = order;
		if (!CustomerUtils.isNull(order.getCustomerOnOrder()))
			this.load(componentId);
		customerShow.show(order.getCustomerOnOrder());
	}

	private void load(String componentId) {
		try {
			customerService
					.findByCodeAndStore(new SearchCustomerByCodeAndStoreDTO(this.order.getCustomerOnOrder().getCode(),
							this.order.getCustomerOnOrder().getStore()))
					.ifPresentOrElse(c -> this.populateCustomerData(c, componentId),
							() -> FacesUtils.error(null, "Cliente não encontrado", null, "growl"));
		} catch (ProcessingException e) {
			processingMsgHelper.displayMessage(e, null, "growl");
		}
	}

	public void load(Order order, String componentId) {
		this.order = order;
		this.load(componentId);
	}

	private void populateCustomerData(Customer customer, String componentId) {
		CustomerOnOrder newCustomer = new CustomerOnOrder(customer);
		this.order.setCustomerOnOrder(newCustomer);
		FacesUtils.ajaxUpdate(componentId);
	}

	public boolean isLoaded(CustomerOnOrder c) {
		return !CustomerUtils.isNull(c);
	}
}
