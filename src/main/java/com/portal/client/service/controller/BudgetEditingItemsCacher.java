package com.portal.client.service.controller;

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

	public Optional<Page<Budget>> initialFetch(String code) {
		budgetService.findByCode(code, 1, pageSize).ifPresentOrElse(budgetPage -> {
			this.budget = this.getBudgetFromPage(budgetPage);
		}, () -> currentPage = null);
		return Optional.ofNullable(currentPage);
	}

	public boolean pageFetch(String code, int page) {
		if (budget == null)
			throw new IllegalStateException("Budget object is null!");
		if (cachedPages.containsKey(page)) {
			BudgetEditingItemsPage cachedPage = cachedPages.get(page);
			currentPage = cachedPage.getPageWrapper();
			budget.removeItems();
			budget.addItems(cachedPage.getItems());
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
		cachePage(page);
		currentPage = page;
		return ((List<Budget>) page.getContent()).get(0);
	}

	/**
	 * Remove item from current cached page
	 * 
	 * @param item
	 */
	public void removeItem(Item item) {
		getCurrentItemPage().removeItem(item);
	}

	/**
	 * Remove items from current cached page
	 * 
	 * @param item
	 */
	public void removeItemS(List<Item> items) {
		getCurrentItemPage().removeItems(items);
	}

	public BudgetEditingItemsPage getCurrentItemPage() {
		return cachedPages.get(currentPage.getPage());
	}

	private void cachePage(Page<Budget> pageToCache) {
		cachedPages.put(pageToCache.getPage(), new BudgetEditingItemsPage(pageToCache));
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
