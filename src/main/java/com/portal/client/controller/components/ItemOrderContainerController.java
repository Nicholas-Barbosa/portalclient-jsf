package com.portal.client.controller.components;

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

import com.portal.client.controller.ProductFileImportObserver;
import com.portal.client.dto.BatchProductSearchDataWrapper;
import com.portal.client.exception.ItemQuantityNotAllowed;
import com.portal.client.service.OrderBehaviorHelper;
import com.portal.client.service.OrderItemQuantityCalculator;
import com.portal.client.util.jsf.FacesUtils;
import com.portal.client.vo.Budget;
import com.portal.client.vo.Item;
import com.portal.client.vo.Order;
import com.portal.client.vo.Product;

@ViewScoped
@Named
public class ItemOrderContainerController implements Serializable, ProductFileImportObserver {

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
	public void onConfirm(BatchProductSearchDataWrapper wrapper) {
		orderHelper.addProducts(order, wrapper);
		FacesUtils.executeScript("updateDtItems();updateTotals()");
	}

	public void handleItemImportReturn(SelectEvent<Budget> event) {
		this.orderHelper.merge(this.order, event.getObject());
	}

	public void handleProductResult(SelectEvent<Optional<Product>> event) {
		event.getObject().ifPresentOrElse(p -> {
			System.out.println("product " + p.getProductTechDetail().getApplication());
			orderHelper.addItem(order, new Item(p));
		}, () -> FacesUtils.warn(null, "Produto não selecionado", "Operação cancelada", "growl"));

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
				? "Item " + itemsToRemove.get(0).getProduct().getCommercialCode() + " removido"
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