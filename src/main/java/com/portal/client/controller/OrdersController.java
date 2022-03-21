package com.portal.client.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.data.PageEvent;

import com.portal.client.dto.OrderSemiProjection;
import com.portal.client.dto.OrderSemiProjectionPage;
import com.portal.client.service.crud.OrderCrudService;
import com.portal.client.ui.lazy.datamodel.LazyBehaviorDataModel;
import com.portal.client.ui.lazy.datamodel.LazyPopulatorUtils;
import com.portal.client.ui.lazy.datamodel.OrderLazyDataModel;
import com.portal.client.util.jsf.FacesUtils;
import com.portal.client.vo.builder.PageBuilder;

@ViewScoped
@Named
public class OrdersController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7525866677101034109L;
	@Inject
	private OrderCrudService orderService;
	private final int pageSize = 15;
	private LazyBehaviorDataModel<OrderSemiProjection> orders;
	private OrderSemiProjection invoiceToView;
	private String keyFilter;
	private boolean filterOn;
	private OrderSemiProjectionPage page;

	public OrdersController() {
		orders = new OrderLazyDataModel();

	}

	public void clearFilter() {
		if (this.filterOn) {
			this.keyFilter = null;
			this.findOrders(1);
			this.filterOn = false;
		}
	}

	public void findOrders(int pageNumber) {
		this.filterOn = this.keyFilter != null && !keyFilter.isBlank() ? true : false;
		orderService.findAll(keyFilter, pageNumber, pageSize).ifPresentOrElse(page -> {
			LazyPopulatorUtils.populate(orders, page);
			this.page = page;
		}, () -> {
			FacesUtils.error(null, "Nenhum resultado encontrado", null, "growl");
			orders.clearCollection();
			this.page = null;
		});
	}

	public void onPage(PageEvent page) {
		this.findOrders(page.getPage() + 1);
	}

	public LazyBehaviorDataModel<OrderSemiProjection> getOrders() {
		return orders;
	}

	public int getPageSize() {
		return pageSize;
	}

	public OrderSemiProjection getInvoiceToView() {
		return invoiceToView;
	}

	public void setInvoiceToView(OrderSemiProjection invoiceToView) {
		this.invoiceToView = invoiceToView;
	}

	public String getKeyFilter() {
		return keyFilter;
	}

	public void setKeyFilter(String keyFilter) {
		this.keyFilter = keyFilter;
	}

	public boolean isFilterOn() {
		return filterOn;
	}

	public OrderSemiProjectionPage getPage() {
		return page;
	}
}
