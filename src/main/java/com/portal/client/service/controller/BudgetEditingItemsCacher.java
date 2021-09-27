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
import com.portal.client.service.crud.BudgetCrudService;
import com.portal.client.vo.Budget;
import com.portal.client.vo.Item;
import com.portal.client.vo.Page;

@Stateful
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class BudgetEditingItemsCacher {

	@Inject
	private BudgetCrudService budgetService;

	private Page<Budget> currentPage;

	private Map<Integer, BudgetEditingItemsPage> cachedPages = new HashMap<>();

	private Budget budget;

	private int pageSize = 10;

	public Optional<Page<Budget>> initialFetch(String code, int page) {
		budgetService.findByCode(code, page, pageSize).ifPresentOrElse(budgetPage -> {
			this.budget = this.getBudgetFromPage(budgetPage);
		}, () -> currentPage = null);
		return Optional.ofNullable(currentPage);
	}

	public boolean pageFetch(String code, int page) {
		if (budget == null)
			throw new IllegalStateException("Budget object is null!");
		if (cachedPages.containsKey(page)) {
			budget.removeItems();
			BudgetEditingItemsPage cachedPage = cachedPages.get(page);
			budget.addItems(cachedPage.getItems());
			currentPage = cachedPage.getPageWrapper();
			return true;
		}
		Optional<Page<Budget>> findByCode = budgetService.findByCode(code, page, pageSize);
		if (findByCode.isPresent()) {
			Budget budgetFromPage = this.getBudgetFromPage(findByCode.get());
			budget.removeItems();
			budget.addItems(budgetFromPage.getItems());
			return true;
		}
		return false;
	}

	private Budget getBudgetFromPage(Page<Budget> page) {
		Budget budget = ((List<Budget>) page.getContent()).get(0);
		cachedPages.put(page.getPage(), new BudgetEditingItemsPage(page, new ArrayList<>(budget.getItems())));
		currentPage = page;
		return budget;
	}

	public void prepareBudgetForDownload(Budget budget) {
		int currenNumPage = currentPage.getPage();
		int totalElements = currentPage.totalItems();

	}

	/**
	 * Remove item from current cached page
	 * 
	 * @param item
	 */
	public void removeItem(Item item) {
		List<Item> items = getItemsOnCurrentPage();
		items.remove(item);
	}

	/**
	 * Remove items from current cached page
	 * 
	 * @param item
	 */
	public void removeItemS(List<Item> items) {
		items.forEach(this::removeItem);
	}

	private BudgetEditingItemsPage getCurrentItemPage() {
		return cachedPages.get(currentPage.getPage());

	}

	private List<Item> getItemsOnCurrentPage() {
		return this.getCurrentItemPage().getItems();
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

	public Page<Budget> getCurrentPage() {
		return currentPage;
	}
}
