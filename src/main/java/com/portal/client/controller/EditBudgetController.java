package com.portal.client.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.ProcessingException;

import com.portal.client.dto.BaseBudget;
import com.portal.client.dto.Customer;
import com.portal.client.dto.CustomerOnOrder;
import com.portal.client.dto.SearchCustomerByCodeAndStoreDTO;
import com.portal.client.service.CustomerService;
import com.portal.client.service.crud.BudgetCrudService;
import com.portal.client.util.jsf.FacesUtils;
import com.portal.client.util.jsf.ProcessingExceptionFacesMessageHelper;

@Named
@ViewScoped
public class EditBudgetController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3352414062899308135L;

	private BudgetCrudService budgetService;

	private CustomerService customerService;

	private ProcessingExceptionFacesMessageHelper exceptionShowMessage;

	private BaseBudget budget;

	private String budgetID;

	private boolean isCustomerDataComplete;

	@Inject
	public EditBudgetController(BudgetCrudService budgetService, CustomerService customerService,
			ProcessingExceptionFacesMessageHelper serverApiExceptionMessageHelper) {
		super();
		this.budgetService = budgetService;
		this.customerService = customerService;
		this.exceptionShowMessage = serverApiExceptionMessageHelper;
	}

	public void getById() {
		try {
			budgetService.findByCode(budgetID).ifPresentOrElse(b -> {
				FacesUtils.info(null, "Orçamento encontrado", null, "growl");
				budget = b;
			}, () -> {
				FacesUtils.error(null, "Orçamento não encontrado", "Código inexistente!", "growl");
			});
		} catch (Exception e) {
			FacesUtils.fatal(null, "Error", e.getMessage(), "growl");
		}
	}

	public void loadCustomerData() {
		try {
			customerService
					.findByCodeAndStore(new SearchCustomerByCodeAndStoreDTO(budget.getCustomerOnOrder().getCode(),
							budget.getCustomerOnOrder().getStore()))
					.ifPresentOrElse(this::populateCustomerData,
							() -> FacesUtils.error(null, "Cliente não encontrado", null, "growl"));
		} catch (ProcessingException e) {
			exceptionShowMessage.displayMessage(e, null, "growl");
		}
	}

	private void populateCustomerData(Customer customer) {
		CustomerOnOrder newCustomer = new CustomerOnOrder(customer);
		budget.setCustomerOnOrder(newCustomer);
		this.isCustomerDataComplete = true;
		FacesUtils.info(null, "Cliente carregado", "Os dados adicionais do cliente foram carregados com sucesso!");
		FacesUtils.ajaxUpdate("panelCustomer", "growl");
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
