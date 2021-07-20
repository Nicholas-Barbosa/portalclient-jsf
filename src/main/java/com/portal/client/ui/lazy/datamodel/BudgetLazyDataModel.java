package com.portal.client.ui.lazy.datamodel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

import com.portal.client.vo.Budget;

public class BudgetLazyDataModel extends LazyDataModelBase<Budget> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2515085178956100553L;

	private List<Budget> budgets;

	@Override
	public void addCollection(Collection<Budget> list) {
		this.budgets = new ArrayList<>(list);

	}

	@Override
	public void clearCollection() {
		budgets.clear();

	}

	@Override
	public Collection<Budget> getCollection() {
		return new ArrayList<>(budgets);
	}

	@Override
	public void turnCollectionElegibleToGB() {
		budgets = null;

	}

	@Override
	public List<Budget> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
		return budgets;
	}

}
