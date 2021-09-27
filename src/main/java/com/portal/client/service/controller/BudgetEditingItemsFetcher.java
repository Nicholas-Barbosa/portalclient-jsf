package com.portal.client.service.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import com.portal.client.dto.BudgetEditingItemsPage;
import com.portal.client.dto.Page;
import com.portal.client.service.crud.BudgetCrudService;
import com.portal.client.vo.Budget;

@Stateful
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class BudgetEditingItemsFetcher {

	@Inject
	private BudgetCrudService budgetService;

	private Page<Budget> currentPage;

	private Map<Integer, BudgetEditingItemsPage> chachedPages = new HashMap<>();

	private Budget budget;

	private int pageSize = 10;

	public Optional<Page<Budget>> initialFetch(String code, int page) {
		budgetService.findByCode(code, page, pageSize).ifPresentOrElse(budgetPage -> {
			this.budget = this.getBudgetFromPage(budgetPage);
		}, () -> currentPage = null);
		return Optional.ofNullable(currentPage);
	}

	public boolean fetch(String code, int page) {
		if (budget == null)
			throw new IllegalStateException("Budget object is null!");
		if (chachedPages.containsKey(page)) {
			budget.removeItems();
			budget.addItems(chachedPages.get(page).getItems());
			return true;
		}
		Optional<Page<Budget>> findByCode = budgetService.findByCode(code, page, pageSize);
		if (findByCode.isPresent()) {
			this.getBudgetFromPage(findByCode.get());
			return true;
		}
		return false;
	}

	private Budget getBudgetFromPage(Page<Budget> page) {
		Budget budget = ((List<Budget>) page.getContent()).get(0);
		chachedPages.put(page.getPage(),
				new BudgetEditingItemsPage(page.getPage(), new ArrayList<>(budget.getItems())));
		currentPage = page;
		return budget;
	}

	public void prepareBudgetForDownload(Budget budget) {
		int currenNumPage = currentPage.getPage();
		int totalElements = currentPage.totalItems();

	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Budget getBudget() {
		return budget;
	}

	public void setBudget(Budget budget) {
		this.budget = budget;
	}
}
