package com.portal.client.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import com.portal.client.controller.show.BudgetExporterShowController;
import com.portal.client.controller.show.OrderBadRequestShowController;
import com.portal.client.repository.OrderBadRequestExcpetion;
import com.portal.client.service.crud.BudgetCrudService;
import com.portal.client.service.crud.OrderCrudService;
import com.portal.client.util.jsf.FacesUtils;
import com.portal.client.vo.Budget;
import com.portal.client.vo.Order;

@Named
@ViewScoped
public class BudgetEditingController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3352414062899308135L;

	private BudgetCrudService budgetService;

	private boolean isCustomerDataComplete;

	private OrderCrudService orderService;

	private Order savedOrder;

	private BudgetExporterShowController exporterShow;

	private Budget budget;

	private String budgetIdToSearch;

	private OrderBadRequestShowController orderBadRequestShowController;

	@Inject
	public BudgetEditingController(BudgetCrudService budgetService,

			OrderCrudService orderService, BudgetExporterShowController exporterShow,
			OrderBadRequestShowController orderBadRequestShowController) {
		super();
		this.budgetService = budgetService;
		this.orderService = orderService;
		this.exporterShow = exporterShow;
		this.orderBadRequestShowController = orderBadRequestShowController;
	}

	public void searchBudget() {
		budgetService.findByCode(budgetIdToSearch).ifPresentOrElse(budget -> {
			this.budget = budget;
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

	}

	public void saveToOrder() {
		if (savedOrder == null) {
			try {
				savedOrder = new Order(this.getBudget());
				orderService.persist(savedOrder);
				FacesUtils.executeScript("PF('effectivedBudget').show();");
				FacesUtils.ajaxUpdate("successPersisted");
			} catch (OrderBadRequestExcpetion e) {
				orderBadRequestShowController.show(e);
			}
			return;

		}
		FacesUtils.warn(null, "Pedido já foi salvo", "Pedido derivado deste orçamento já foi salvo!", "growl");
	}

	public void confirmBudgetEditing() {
		budgetService.update(this.getBudget());
		FacesUtils.info(null, "Orçamento atualizado", null, "growl");
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

}
