package com.farawaybr.portal.jsf.controller.components;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;

import com.farawaybr.portal.dto.BatchProductSearchDataWrapper;
import com.farawaybr.portal.exception.ItemQuantityNotAllowed;
import com.farawaybr.portal.jsf.controller.ProductFileImportComponentObserver;
import com.farawaybr.portal.service.OrderBehaviorHelper;
import com.farawaybr.portal.service.OrderItemQuantityCalculator;
import com.farawaybr.portal.util.jsf.FacesUtils;
import com.farawaybr.portal.vo.Budget;
import com.farawaybr.portal.vo.Item;
import com.farawaybr.portal.vo.Order;
import com.farawaybr.portal.vo.Product;

@ViewScoped
@Named
public class ItemOrderContainerController implements Serializable, ProductFileImportComponentObserver, ProductSearchObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = -743976325979246705L;
	private List<Item> itemsToRemove;

	@Inject
	private OrderBehaviorHelper orderHelper;

	@Inject
	private OrderItemQuantityCalculator itemQuantityCalculator;

	private Order order;

	private int pageSize = 10;

	private int onRowItemQuantity;
	

	public ItemOrderContainerController() {
		itemsToRemove = new ArrayList<>();
	}

	@Override
	public void onConfirm(Product product) {
		orderHelper.addItem(order, new Item(null,product));

	}

	@Override
	public void onConfirm(BatchProductSearchDataWrapper wrapper) {
		orderHelper.addProducts(order, wrapper);
		FacesUtils.executeScript("updateDtItems();updateTotals()");
	}

	public void handleItemImportReturn(SelectEvent<Budget> event) {
		this.orderHelper.merge(this.order, event.getObject());
	}

	public void onRowItemEdit(RowEditEvent<Item> event) {
		try {

			itemQuantityCalculator.calc(order, event.getObject(), onRowItemQuantity);
		} catch (ItemQuantityNotAllowed e) {
			FacesUtils.error(null, e.getMessage(), null);
			PrimeFaces.current().ajax().update("growl");
		}
	}

	public void removeItem(Item item) {
		orderHelper.removeItem(order, item);
		itemsToRemove.remove(item);
		item = null;
	}

	public void removeItems() {
		orderHelper.removeItems(order, itemsToRemove);
		String message = itemsToRemove.size() == 1
				? "Item " + itemsToRemove.get(0).getCommercialCode() + " removido"
				: itemsToRemove.size() + " itens removidos.";
		itemsToRemove.clear();
		FacesUtils.info(null, message, null, "growl");
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
		return items == 0 ? " N??o h?? itens!" : items == 1 ? "H?? 1 item." : "H?? " + items + " itens.";
	}

	public String getDeleteButtonMessage() {
		return hasSelectedItems() ? itemsToRemove.size() == 1 ? itemsToRemove.size() + " item selecionado"
				: itemsToRemove.size() + " itens selecionados" : null;
	}

	public int getPageSize() {
		return pageSize;
	}

	public List<Item> getItems() {
		return order == null ? null : order.getItems();
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public int getOnRowItemQuantity() {
		return onRowItemQuantity;
	}

	public void setOnRowItemQuantity(int onRowItemQuantity) {
		this.onRowItemQuantity = onRowItemQuantity;
	}

	
}