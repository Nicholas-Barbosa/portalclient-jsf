package com.portal.client.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.portal.client.service.OrderCommonBehaviorHelper;
import com.portal.client.util.jsf.FacesUtils;
import com.portal.client.vo.Item;
import com.portal.client.vo.Order;

@ViewScoped
@Named
public class DtableBudgetEditingController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4688568430204064894L;
	private List<Item> items;
	@Inject
	private OrderCommonBehaviorHelper helper;

	public DtableBudgetEditingController() {
		items = new ArrayList<>();
	}

	public void deleteItems(Order order) {
		helper.removeItems(order, items);
		items.clear();
		FacesUtils.info(null, "Itens removidos", null, "growl");
	}

	public boolean hasSelectedItems() {
		return items.size() >= 1;
	}

	public boolean disableDeleteProductsBtn() {
		return !hasSelectedItems();
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public String getDeleteButtonMessage() {
		return hasSelectedItems()
				? items.size() == 1 ? items.size() + " item selecionado" : items.size() + " itens selecionados"
				: null;
	}
}
