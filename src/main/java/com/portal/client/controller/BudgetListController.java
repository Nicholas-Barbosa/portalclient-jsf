package com.portal.client.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.portal.client.service.BudgetService;
import com.portal.client.ui.lazy.datamodel.LazyDataModelBase;
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

	public LazyDataModelBase<Budget> getBudgets() {
		return budgets;
	}

}
