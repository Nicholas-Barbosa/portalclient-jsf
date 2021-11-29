package com.portal.client.controller.components;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.ProcessingException;

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
