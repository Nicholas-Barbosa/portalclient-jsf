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

import com.portal.client.controller.show.BudgetExporterShowController;
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

	private boolean isCustomerDataComplete;

	private CustomerDetailShowController customerShow;

	private OrderCommonBehaviorHelper orderHelper;

	private OrderItemQuantityCalculator ordemQuantityCalculator;

	private OrderCrudService orderService;

	private int onRowItemQuantity;

	private Order savedOrder;

	private BudgetExporterShowController exporterShow;

	private DtableBudgetEditingController dtableController;
	
	@Inject
	public BudgetEditingController(BudgetCrudService budgetService, CustomerService customerService,
			ProcessingExceptionFacesMessageHelper serverApiExceptionMessageHelper,
			CustomerDetailShowController customerShow, OrderCommonBehaviorHelper orderHelper,
			OrderItemQuantityCalculator ordemQuantityCalculator, OrderCrudService orderService,
			BudgetExporterShowController exporterShow,DtableBudgetEditingController dtableController) {
		super();
		this.budgetService = budgetService;
		this.customerService = customerService;
		this.exceptionShowMessage = serverApiExceptionMessageHelper;
		this.customerShow = customerShow;
		this.orderHelper = orderHelper;
		this.ordemQuantityCalculator = ordemQuantityCalculator;
		this.orderService = orderService;
		this.exporterShow = exporterShow;
		this.dtableController = dtableController;
	}

	public void export() {
		if (this.getBudget().getCustomerOnOrder().getName() != null)
			exporterShow.show(this.getBudget());
		else
			FacesUtils.warn(null, "Não é possível exportar neste momento",
					"Carregue os dados do cliente deste orçamento", "growl");
	}

	public void handleItemImportReturn(SelectEvent<Budget> event) {
		this.orderHelper.merge(this.getBudget(), event.getObject());
		FacesUtils.ajaxUpdate("dtItems", "panelTotals");
	}

	public void saveToOrder() {
		if (savedOrder == null) {
			savedOrder = new Order(this.getBudget());
			orderService.persist(savedOrder);
			FacesUtils.executeScript("PF('effectivedBudget').show();");
			FacesUtils.ajaxUpdate("successPersisted");
			return;
		}
		FacesUtils.warn(null, "Pedido já foi salvo", "Pedido derivado deste orçamento já foi salvo!", "growl");
	}

	public void confirmBudgetEditing() {
		budgetService.update(this.getBudget());
		FacesUtils.info(null, "Orçamento atualizado", null, "growl");
	}

	public void onRowItemEdit(RowEditEvent<Item> event) {
		try {
			ordemQuantityCalculator.calc(this.getBudget(), event.getObject(), onRowItemQuantity);
		} catch (ItemQuantityNotAllowed e) {
			FacesUtils.error(null, e.getMessage(), null);
			PrimeFaces.current().ajax().update("growl");
		}
	}

	public void handleProductResult(SelectEvent<Optional<Product>> event) {
		event.getObject().ifPresentOrElse(p -> {
			orderHelper.addItem(this.getBudget(), Item.product(p));
			FacesUtils.ajaxUpdate("dtItems", "totals");
		}, () -> FacesUtils.warn(null, "Produto não selecionado", "Operação cancelada", "growl"));

	}


	public void showCustomerData() {
		if (!isCustomerDataComplete)
			this.loadAdditionalCustomerData();
		customerShow.show(this.getBudget().getCustomerOnOrder());
	}

	private void loadAdditionalCustomerData() {
		try {
			customerService
					.findByCodeAndStore(new SearchCustomerByCodeAndStoreDTO(this.getBudget().getCustomerOnOrder().getCode(),
							this.getBudget().getCustomerOnOrder().getStore()))
					.ifPresentOrElse(this::populateCustomerData,
							() -> FacesUtils.error(null, "Cliente não encontrado", null, "growl"));
		} catch (ProcessingException e) {
			exceptionShowMessage.displayMessage(e, null, "growl");
		}
	}

	private void populateCustomerData(Customer customer) {
		CustomerOnOrder newCustomer = new CustomerOnOrder(customer);
		this.getBudget().setCustomerOnOrder(newCustomer);
		this.isCustomerDataComplete = true;
		FacesUtils.ajaxUpdate("panelCustomer");
	}

	public String getBudgetID() {
		return dtableController.getBudgetId();
	}

	public void setBudgetID(String budgetID) {
		dtableController.setBudgetId(budgetID);
	}

	public Budget getBudget() {
		return dtableController.getBudget();
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

	public DtableBudgetEditingController getDtableController() {
		return dtableController;
	}
	
}
