package com.portal.client.controller;

import java.io.Serializable;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.portal.client.dto.BaseBudget;
import com.portal.client.dto.Customer;
import com.portal.client.dto.CustomerOnOrder;
import com.portal.client.dto.CustomerOnOrder.CustomerType;
import com.portal.client.dto.SearchCustomerByCodeAndStoreDTO;
import com.portal.client.service.CustomerService;
import com.portal.client.service.crud.BudgetCrudService;
import com.portal.client.util.jsf.FacesUtils;
import com.portal.client.util.jsf.ServerApiExceptionFacesMessageHelper;

@Named
@ViewScoped
public class EditBudgetController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3352414062899308135L;

	private BudgetCrudService budgetService;

	private CustomerService customerService;

	private ServerApiExceptionFacesMessageHelper serverApiExceptionMessageHelper;

	private BaseBudget budget;

	private String budgetID;

	private boolean isCustomerDataComplete;

	@Inject
	public EditBudgetController(BudgetCrudService budgetService, CustomerService customerService,
			ServerApiExceptionFacesMessageHelper serverApiExceptionMessageHelper) {
		super();
		this.budgetService = budgetService;
		this.customerService = customerService;
		this.serverApiExceptionMessageHelper = serverApiExceptionMessageHelper;
	}

	public void loadCustomerData() {
		try {
			System.out.println("load customer data!");
			customerService
					.findByCodeAndStore(new SearchCustomerByCodeAndStoreDTO(budget.getCustomerOnOrder().getCode(),
							budget.getCustomerOnOrder().getStore()))
					.ifPresentOrElse(this::populateCustomerData,
							() -> FacesUtils.error(null, "Cliente não encontrado", null, "growl"));
		} catch (SocketTimeoutException | SocketException | TimeoutException e) {
			serverApiExceptionMessageHelper.displayMessage(e, null, "growl");
		}
	}

	private void populateCustomerData(Customer customer) {
		CustomerOnOrder newCustomer = new CustomerOnOrder(customer, CustomerType.NORMAL,
				budget.getCustomerOnOrder().getMessage());
		budget.setCustomerOnOrder(newCustomer);
		this.isCustomerDataComplete = true;
		FacesUtils.info(null, "Cliente carregado", "Os dados adicionais do cliente foram carregados com sucesso!");
		FacesUtils.ajaxUpdate("panelCustomer", "growl");
	}

	public void getById() {
		try {
			budgetService.findByCode(budgetID).ifPresentOrElse(b -> {
				FacesUtils.info(null, "Orçamento encontrado", null, "growl");
				budget = b;
			}, () -> {
				FacesUtils.error(null, "Orçamento não encontrado", "Código inexistente!", "growl");
			});
		} catch (SocketTimeoutException | SocketException | TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			FacesUtils.fatal(null, "Error", e.getMessage(), "growl");
		}
	}

	public String getBudgetID() {
		return budgetID;
	}

	public void setBudgetID(String budgetID) {
		this.budgetID = budgetID;
	}

	public BaseBudget getBudget() {
		return budget;
	}

	public boolean isCustomerDataComplete() {
		return isCustomerDataComplete;
	}

}
