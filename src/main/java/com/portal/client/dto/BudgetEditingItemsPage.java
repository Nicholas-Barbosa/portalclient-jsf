package com.portal.client.dto;

import java.util.List;

import com.portal.client.vo.Budget;
import com.portal.client.vo.Item;
import com.portal.client.vo.Page;

public class BudgetEditingItemsPage {

	private Page<Budget> page;
	private List<Item> items;

	public BudgetEditingItemsPage(Page<Budget> page, List<Item> items) {
		super();
		this.page = page;
		this.items = items;
	}

	public Page<Budget> getPageWrapper() {
		return page;
	}

	public int getPage() {
		return page.getPage();
	}

	public int getPageSize() {
		return page.getPageSize();
	}

	public List<Item> getItems() {
		return items;
	}

}
