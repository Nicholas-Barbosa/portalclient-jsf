package com.portal.client.controller;

import java.io.Serializable;
import java.util.Optional;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.ProcessingException;

import org.primefaces.event.SelectEvent;

import com.portal.client.controller.show.CustomerDetailShowController;
import com.portal.client.dto.Customer;
import com.portal.client.dto.CustomerOnOrder;
import com.portal.client.dto.SearchCustomerByCodeAndStoreDTO;
import com.portal.client.service.CustomerService;
import com.portal.client.service.OrderCommonBehaviorHelper;
import com.portal.client.service.crud.BudgetCrudService;
import com.portal.client.util.jsf.FacesUtils;
import com.portal.client.util.jsf.ProcessingExceptionFacesMessageHelper;
import com.portal.client.vo.Budget;
import com.portal.client.vo.Item;
import com.portal.client.vo.Product;

@Named
@ViewScoped
public class BudgetEditingController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3352414062899308135L;

	private BudgetCrudService budgetService;

	private CustomerService customerService;

	private ProcessingExceptionFacesMessageHelper exceptionShowMessage;

	private Budget budget;

	private String budgetID;

	private boolean isCustomerDataComplete;

	private CustomerDetailShowController customerShow;

	private OrderCommonBehaviorHelper orderHelper;

	@Inject
	public BudgetEditingController(BudgetCrudService budgetService, CustomerService customerService,
			ProcessingExceptionFacesMessageHelper serverApiExceptionMessageHelper,
			CustomerDetailShowController customerShowOrderCommonBehaviorHelper,
			CustomerDetailShowController customerShow, OrderCommonBehaviorHelper orderHelper) {
		super();
		this.budgetService = budgetService;
		this.customerService = customerService;
		this.exceptionShowMessage = serverApiExceptionMessageHelper;
		this.customerShow = customerShow;
		this.orderHelper = orderHelper;
	}

	public void confirmBudgetEditing() {
		try {
			budgetService.update(budget);
		} catch (Exception e) {
			FacesUtils.fatal(null, "Fatal", "Exception lançada!", "growl");
			e.printStackTrace();
		}
	}

	public void handleProductResult(SelectEvent<Optional<Product>> event) {
		event.getObject().ifPresentOrElse(p -> {
			orderHelper.addItem(budget, Item.product(p));
			FacesUtils.ajaxUpdate("dtItems", "totals");
		}, () -> FacesUtils.warn(null, "Produto não selecionado", "Operação cancelada", "growl"));

	}

	public void find() {
		budgetService.findByCode(budgetID).ifPresentOrElse(b -> {
			budget = b;
		}, () -> {
			FacesUtils.error(null, "Orçamento não encontrado!",
					"Não foi possível encontrar nenhum orçamento com este identificador", "growl");
		});

	}

	public void showCustomerData() {
		if (!isCustomerDataComplete)
			this.loadAdditionalCustomerData();
		customerShow.show(budget.getCustomerOnOrder());
	}

	private void loadAdditionalCustomerData() {
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
		FacesUtils.ajaxUpdate("panelCustomer");
	}

	public String getBudgetID() {
		return budgetID;
	}

	public void setBudgetID(String budgetID) {
		this.budgetID = budgetID;
	}

	public Budget getBudget() {
		return budget;
	}

	public boolean isCustomerDataComplete() {
		return isCustomerDataComplete;
	}

}
