package com.portal.client.controller;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

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
public class ViewOrderIncompleteCustomerData {

	private Order order;
	private CustomerDetailShowController customerShow;
	private CustomerService customerService;
	private ProcessingExceptionFacesMessageHelper processingMsgHelper;

	@Inject
	public ViewOrderIncompleteCustomerData(CustomerDetailShowController customerShow, CustomerService customerService,
			ProcessingExceptionFacesMessageHelper processingMsgHelper) {
		super();
		this.customerShow = customerShow;
		this.customerService = customerService;
		this.processingMsgHelper = processingMsgHelper;
	}

	public void view(Order order) {
		this.order = order;
		if (!CustomerUtils.isNull(order.getCustomerOnOrder()))
			this.loadAdditionalCustomerData();
		customerShow.show(order.getCustomerOnOrder());
	}

	private void loadAdditionalCustomerData() {
		try {
			customerService
					.findByCodeAndStore(new SearchCustomerByCodeAndStoreDTO(this.order.getCustomerOnOrder().getCode(),
							this.order.getCustomerOnOrder().getStore()))
					.ifPresentOrElse(this::populateCustomerData,
							() -> FacesUtils.error(null, "Cliente não encontrado", null, "growl"));
		} catch (ProcessingException e) {
			processingMsgHelper.displayMessage(e, null, "growl");
		}
	}

	private void populateCustomerData(Customer customer) {
		CustomerOnOrder newCustomer = new CustomerOnOrder(customer);
		this.order.setCustomerOnOrder(newCustomer);
		FacesUtils.ajaxUpdate("panelCustomer");
	}

}
