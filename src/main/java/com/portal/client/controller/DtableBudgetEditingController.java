package com.portal.client.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.primefaces.event.data.PageEvent;

import com.portal.client.service.OrderCommonBehaviorHelper;
import com.portal.client.service.controller.BudgetEditingItemsFetcher;
import com.portal.client.ui.lazy.datamodel.ItemLazyDataModel;
import com.portal.client.ui.lazy.datamodel.LazyBehaviorDataModel;
import com.portal.client.ui.lazy.datamodel.LazyPopulatorUtils;
import com.portal.client.util.jsf.FacesUtils;
import com.portal.client.vo.Budget;
import com.portal.client.vo.Item;

@Dependent
public class DtableBudgetEditingController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -743976325979246705L;
	private List<Item> itemsToRemove;
	private LazyBehaviorDataModel<Item> items;
	@Inject
	private OrderCommonBehaviorHelper helper;

	@EJB
	private BudgetEditingItemsFetcher itemsFetcher;

	private int pageSize = 10;

	private String budgetId;

	public DtableBudgetEditingController() {
		itemsToRemove = new ArrayList<>();
		items = new ItemLazyDataModel();
	}

	public void onPage(PageEvent page) {
		itemsFetcher.fetch(budgetId, page.getPage() + 1);
	}

	public void initialFind() {
		itemsFetcher.initialFetch(budgetId, 1).ifPresentOrElse(budgetPage -> {
			Budget budget = ((List<Budget>) budgetPage.getContent()).get(0);
			LazyPopulatorUtils.populate(items, budgetPage, budget.getItems());
		}, () -> {
			FacesUtils.error(null, "Orçamento não encontrado!",
					"Não foi possível encontrar nenhum orçamento com este identificador", "growl");
		});

	}

	public void deleteItems(LazyBehaviorDataModel<Item> lazyItems) {
		helper.removeItems(itemsFetcher.getBudget(), itemsToRemove);
		itemsToRemove.clear();
		lazyItems.getWrappedData().removeAll(itemsToRemove);
		FacesUtils.info(null, "Itens removidos", null, "growl");
	}

	public boolean hasSelectedItems() {
		return itemsToRemove.size() >= 1;
	}

	public boolean disableDeleteProductsBtn() {
		return !hasSelectedItems();
	}

	public List<Item> getItemsToRemove() {
		return itemsToRemove;
	}

	public void setItemsToRemove(List<Item> items) {
		this.itemsToRemove = items;
	}

	public LazyBehaviorDataModel<Item> getItems() {
		return items;
	}

	public void setItems(LazyBehaviorDataModel<Item> items) {
		this.items = items;
	}

	public void setBudget(Budget budget) {
		this.itemsFetcher.setBudget(budget);
	}

	public Budget getBudget() {
		return itemsFetcher.getBudget();
	}

	public String getDeleteButtonMessage() {
		return hasSelectedItems() ? itemsToRemove.size() == 1 ? itemsToRemove.size() + " item selecionado"
				: itemsToRemove.size() + " itens selecionados" : null;
	}

	public String getBudgetId() {
		return budgetId;
	}

	public void setBudgetId(String budgetId) {
		this.budgetId = budgetId;
	}

	public int getPageSize() {
		return pageSize;
	}
}
