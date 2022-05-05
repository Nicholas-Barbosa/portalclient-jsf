package com.farawaybr.portal.dto;

import java.util.ArrayList;
import java.util.List;

import com.farawaybr.portal.vo.Budget;
import com.farawaybr.portal.vo.Item;
import com.farawaybr.portal.vo.Page;

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
