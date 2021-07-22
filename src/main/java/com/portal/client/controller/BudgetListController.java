package com.portal.client.controller;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.data.PageEvent;

import com.portal.client.service.BudgetService;
import com.portal.client.ui.lazy.datamodel.BudgetLazyDataModel;
import com.portal.client.ui.lazy.datamodel.LazyDataModelBase;
import com.portal.client.ui.lazy.datamodel.LazyPopulateUtils;
import com.portal.client.vo.Budget;

@RequestScoped
@Named
public class BudgetListController {

	private BudgetService buService;

	private LazyDataModelBase<Budget> budgets;

	@Inject
	public BudgetListController(BudgetService buService) {
		super();
		this.buService = buService;
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

	public LazyDataModelBase<Budget> getBudgets() {
		return budgets;
	}

}
