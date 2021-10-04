package com.portal.client.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;

import com.portal.client.exception.ItemQuantityNotAllowed;
import com.portal.client.service.OrderCommonBehaviorHelper;
import com.portal.client.service.OrderItemQuantityCalculator;
import com.portal.client.util.jsf.FacesUtils;
import com.portal.client.vo.Budget;
import com.portal.client.vo.Item;

@Dependent
public class DtableItemController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -743976325979246705L;
	private List<Item> itemsToRemove;

	@Inject
	private OrderCommonBehaviorHelper helper;

	@Inject
	private OrderItemQuantityCalculator itemQuantityCalculator;

	private Budget budget;

	private int pageSize = 10;

	private int onRowItemQuantity;

	public DtableItemController() {
		itemsToRemove = new ArrayList<>();
	}

	public void onRowItemEdit(RowEditEvent<Item> event) {
		try {
			itemQuantityCalculator.calc(budget, event.getObject(), onRowItemQuantity);
		} catch (ItemQuantityNotAllowed e) {
			FacesUtils.error(null, e.getMessage(), null);
			PrimeFaces.current().ajax().update("growl");
		}
	}

	public void removeItem(Item item) {
		helper.removeItem(budget, item);
		itemsToRemove.remove(item);
		item = null;
	}

	public void removeItems() {
		helper.removeItems(budget, itemsToRemove);
		itemsToRemove.clear();
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

	public String getDtableFooterMessage() {
		int items = this.getItems() == null ? 0 : this.getItems().size();
		return items == 0 ? " Não há itens!" : items == 1 ? "Há 1 item." : "Há " + items + " itens.";
	}

	public String getDeleteButtonMessage() {
		return hasSelectedItems() ? itemsToRemove.size() == 1 ? itemsToRemove.size() + " item selecionado"
				: itemsToRemove.size() + " itens selecionados" : null;
	}

	public int getPageSize() {
		return pageSize;
	}

	public List<Item> getItems() {
		return budget == null ? null : budget.getItems();
	}

	public Budget getBudget() {
		return budget;
	}

	public void setBudget(Budget budget) {
		this.budget = budget;
	}

	public int getOnRowItemQuantity() {
		return onRowItemQuantity;
	}

	public void setOnRowItemQuantity(int onRowItemQuantity) {
		this.onRowItemQuantity = onRowItemQuantity;
	}
}