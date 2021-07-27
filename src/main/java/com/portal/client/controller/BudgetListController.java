package com.portal.client.controller;

import java.io.Serializable;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.event.data.PageEvent;

import com.portal.client.dto.BudgetSemiProjection;
import com.portal.client.service.crud.BudgetCrudService;
import com.portal.client.ui.lazy.datamodel.BudgetLazyDataModel;
import com.portal.client.ui.lazy.datamodel.LazyDataModelBase;
import com.portal.client.ui.lazy.datamodel.LazyPopulateUtils;

@ViewScoped
@Named
public class BudgetListController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9093802333014411573L;

	private BudgetCrudService buService;

	private LazyDataModelBase<BudgetSemiProjection> budgets;

	@Inject
	public BudgetListController(BudgetCrudService buService) {
		super();
		this.buService = buService;
	}

	public void showEditBudget(BudgetSemiProjection budget) {
		PrimeFaces.current().executeScript("alert('orçamento " + budget.getCode() + "')");
	}

	public void loadBudgets(int page) {
		if (budgets == null)
			this.budgets = new BudgetLazyDataModel();
		try {
			LazyPopulateUtils.populate(budgets, buService.findAll(page, 15));
		} catch (SocketTimeoutException | SocketException | TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void onPage(PageEvent page) {
		this.loadBudgets(page.getPage() + 1);
	}

	public LazyDataModelBase<BudgetSemiProjection> getBudgets() {
		return budgets;
	}

}
