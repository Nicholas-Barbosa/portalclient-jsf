package com.farawaybr.portal.jsf.controller.components;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.ProcessingException;

import com.farawaybr.portal.dto.CustomerUtils;
import com.farawaybr.portal.dto.SearchCustomerByCodeAndStoreDTO;
import com.farawaybr.portal.service.CustomerService;
import com.farawaybr.portal.util.jsf.FacesUtils;
import com.farawaybr.portal.util.jsf.ProcessingExceptionFacesMessageHelper;
import com.farawaybr.portal.vo.Customer;
import com.farawaybr.portal.vo.CustomerOnOrder;
import com.farawaybr.portal.vo.Order;

@RequestScoped
@Named
public class CustomerPanelController {

	private CustomerService customerService;
	private ProcessingExceptionFacesMessageHelper processingMsgHelper;

	@Inject
	public CustomerPanelController(CustomerService customerService,
			ProcessingExceptionFacesMessageHelper processingMsgHelper) {
		super();
		this.customerService = customerService;
		this.processingMsgHelper = processingMsgHelper;
	}

	public void load(Order order, String componentId) {
		try {
			if (order != null && order.getCustomerOnOrder().getCnpj() == null)
				customerService
						.findByCodeAndStore(new SearchCustomerByCodeAndStoreDTO(order.getCustomerOnOrder().getCode(),
								order.getCustomerOnOrder().getStore()))
						.ifPresentOrElse(c -> this.populateCustomerData(order, c, componentId),
								() -> FacesUtils.error(null, "Cliente não encontrado", null, "growl"));

		} catch (ProcessingException e) {
			processingMsgHelper.displayMessage(e, null, "growl");
		}
	}

	private void populateCustomerData(Order order, Customer customer, String componentId) {
		CustomerOnOrder newCustomer = new CustomerOnOrder(customer);
		order.setCustomerOnOrder(newCustomer);
		FacesUtils.ajaxUpdate(componentId);
	}

	public boolean isLoaded(CustomerOnOrder c) {
		return !CustomerUtils.isNull(c);
	}
}
