package com.portal.client.controller;

import java.io.Serializable;
import java.util.Optional;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.ProcessingException;

import org.primefaces.event.SelectEvent;

import com.portal.client.controller.show.BudgetExporterShowController;
import com.portal.client.controller.show.CustomerDetailShowController;
import com.portal.client.dto.Customer;
import com.portal.client.dto.CustomerOnOrder;
import com.portal.client.dto.SearchCustomerByCodeAndStoreDTO;
import com.portal.client.repository.OrderBadRequestExcpetion;
import com.portal.client.service.CustomerService;
import com.portal.client.service.OrderCommonBehaviorHelper;
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

	private OrderCrudService orderService;

	private Order savedOrder;

	private BudgetExporterShowController exporterShow;

	private DtableItemController dtableController;

	private Budget budget;

	private String budgetIdToSearch;

	@Inject
	public BudgetEditingController(BudgetCrudService budgetService, CustomerService customerService,
			ProcessingExceptionFacesMessageHelper serverApiExceptionMessageHelper,
			CustomerDetailShowController customerShow, OrderCommonBehaviorHelper orderHelper,
			OrderCrudService orderService, BudgetExporterShowController exporterShow,
			DtableItemController dtableController) {
		super();
		this.budgetService = budgetService;
		this.customerService = customerService;
		this.exceptionShowMessage = serverApiExceptionMessageHelper;
		this.customerShow = customerShow;
		this.orderHelper = orderHelper;
		this.orderService = orderService;
		this.exporterShow = exporterShow;
		this.dtableController = dtableController;
	}

	public void searchBudget() {
		budgetService.findByCode(budgetIdToSearch).ifPresentOrElse(budget -> {
			this.budget = budget;
			this.dtableController.setOrder(budget);
		}, () -> {
			this.budget = null;
			FacesUtils.error(null, "Orçamento não encontrado", null, "growl");
		});
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
		FacesUtils.ajaxUpdate("panelTotals");
	}

	public void saveToOrder() {
		if (savedOrder == null) {
			try {
				savedOrder = new Order(this.getBudget());
				orderService.persist(savedOrder);
				FacesUtils.executeScript("PF('effectivedBudget').show();");
				FacesUtils.ajaxUpdate("successPersisted");
			} catch (OrderBadRequestExcpetion e) {
				// TODO: handle exception
			}
			return;

		}
		FacesUtils.warn(null, "Pedido já foi salvo", "Pedido derivado deste orçamento já foi salvo!", "growl");
	}

	public void confirmBudgetEditing() {
		budgetService.update(this.getBudget());
		FacesUtils.info(null, "Orçamento atualizado", null, "growl");
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
					.findByCodeAndStore(
							new SearchCustomerByCodeAndStoreDTO(this.getBudget().getCustomerOnOrder().getCode(),
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

	public String getBudgetIdToSearch() {
		return budgetIdToSearch;
	}

	public void setBudgetIdToSearch(String budgetIdToSearch) {
		this.budgetIdToSearch = budgetIdToSearch;
	}

	public Budget getBudget() {
		return budget;
	}

	public boolean isCustomerDataComplete() {
		return isCustomerDataComplete;
	}

	public Order getSavedOrder() {
		return savedOrder;
	}

	public DtableItemController getDtableController() {
		return dtableController;
	}

}
