package com.portal.client.dto;

import java.util.ArrayList;
import java.util.List;

import com.portal.client.vo.Budget;
import com.portal.client.vo.Item;
import com.portal.client.vo.Page;

public class BudgetEditingItemsPage {

	private Page<Budget> page;
	private List<Item> items;

	public BudgetEditingItemsPage(Page<Budget> page) {
		super();
		this.page = page;
		this.items = new ArrayList<>(((List<Budget>) page.getContent()).get(0).getItems());
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

	public boolean removeItem(Item tem) {
		return this.items.remove(tem);
	}

	public boolean removeItems(List<Item> tems) {
		return this.items.removeAll(tems);
	}
}
