package com.portal.client.dto;

import java.util.List;

import com.portal.client.vo.Item;

public class BudgetEditingItemsPage {

	private int page;
	private List<Item> items;

	public BudgetEditingItemsPage(int page, List<Item> items) {
		super();
		this.page = page;
		this.items = items;
	}

	public int getPage() {
		return page;
	}

	public List<Item> getItems() {
		return items;
	}

}
