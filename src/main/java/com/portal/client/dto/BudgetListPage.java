package com.portal.client.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class BudgetListPage implements Page<BudgetList> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7241484823137746657L;

	private final int totalItems;
	private final int totalPage;
	private final int pageSize;
	private final int page;
	private final List<BudgetList> budgets;

	@JsonbCreator
	public BudgetListPage(@JsonbProperty("total_items") int totalItems, @JsonbProperty("total_page") int totalPage,
			@JsonbProperty("page_size") int pageSize, @JsonbProperty("page") int page,
			@JsonbProperty("budget") List<BudgetList> budgets) {
		super();
		this.totalItems = totalItems;
		this.totalPage = totalPage;
		this.pageSize = pageSize;
		this.page = page;
		this.budgets = budgets;
	}

	@Override
	public int getPageSize() {
		// TODO Auto-generated method stub
		return pageSize;
	}

	@Override
	public int getPage() {
		// TODO Auto-generated method stub
		return page;
	}

	@Override
	public int totalPages() {
		// TODO Auto-generated method stub
		return totalPage;
	}

	@Override
	public int totalItems() {
		// TODO Auto-generated method stub
		return totalItems;
	}

	@Override
	public Collection<BudgetList> getContent() {
		// TODO Auto-generated method stub
		return new ArrayList<>(budgets);
	}

}
