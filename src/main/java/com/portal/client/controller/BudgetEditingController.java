package com.portal.client.controller;

import java.io.Serializable;
import java.util.Optional;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.ProcessingException;

import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;

import com.portal.client.controller.show.CustomerDetailShowController;
import com.portal.client.dto.Customer;
import com.portal.client.dto.CustomerOnOrder;
import com.portal.client.dto.SearchCustomerByCodeAndStoreDTO;
import com.portal.client.exception.ItemQuantityNotAllowed;
import com.portal.client.service.CustomerService;
import com.portal.client.service.OrderCommonBehaviorHelper;
import com.portal.client.service.OrderItemQuantityCalculator;
import com.portal.client.service.crud.BudgetCrudService;
import com.portal.client.service.crud.OrderCrudService;
import com.portal.client.util.jsf.FacesUtils;
import com.portal.client.util.jsf.ProcessingExceptionFacesMessageHelper;
import com.portal.client.vo.Budget;
import com.portal.client.vo.Item;
import com.portal.client.vo.Order;
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

	private OrderItemQuantityCalculator ordemQuantityCalculator;

	private OrderCrudService orderService;

	private int onRowItemQuantity;

	private Order savedOrder;

	@Inject
	public BudgetEditingController(BudgetCrudService budgetService, CustomerService customerService,
			ProcessingExceptionFacesMessageHelper serverApiExceptionMessageHelper,
			CustomerDetailShowController customerShowOrderCommonBehaviorHelper,
			CustomerDetailShowController customerShow, OrderCommonBehaviorHelper orderHelper,
			OrderItemQuantityCalculator ordemQuantityCalculator, OrderCrudService orderService) {
		super();
		this.budgetService = budgetService;
		this.customerService = customerService;
		this.exceptionShowMessage = serverApiExceptionMessageHelper;
		this.customerShow = customerShow;
		this.orderHelper = orderHelper;
		this.ordemQuantityCalculator = ordemQuantityCalculator;
		this.orderService = orderService;
	}

	public void saveToOrder() {
		if (savedOrder == null) {
			savedOrder = new Order(budget);
			orderService.persist(savedOrder);
			FacesUtils.executeScript("PF('effectivedBudget').show();");
			FacesUtils.ajaxUpdate("successPersisted");
			return;
		}
		FacesUtils.warn(null, "Pedido já foi salvo", "Pedido derivado deste orçamento já foi salvo!", "growl");
	}

	public void confirmBudgetEditing() {
		budgetService.update(budget);
		FacesUtils.info(null, "Orçamento atualizado", null, "growl");
	}

	public void onRowItemEdit(RowEditEvent<Item> event) {
		try {
			ordemQuantityCalculator.calc(budget, event.getObject(), onRowItemQuantity);
		} catch (ItemQuantityNotAllowed e) {
			FacesUtils.error(null, e.getMessage(), null);
			PrimeFaces.current().ajax().update("growl");
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

	public int getOnRowItemQuantity() {
		return onRowItemQuantity;
	}

	public void setOnRowItemQuantity(int onRowItemQuantity) {
		this.onRowItemQuantity = onRowItemQuantity;
	}

	public Order getSavedOrder() {
		return savedOrder;
	}
}
