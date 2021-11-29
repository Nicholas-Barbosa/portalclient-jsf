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

	public OrdersController() {
		orders = new OrderLazyDataModel();
	}

	public void getOrders(int page) {
		orderService.findAll(page, pageSize).ifPresentOrElse(o -> {
			OrderSemiProjectionPage wrapper = o;
			LazyPopulatorUtils.populate(orders, wrapper);
		}, () -> FacesUtils.error(null, "Não há pedidos", "Não há pedidos para o representante", "growl"));

	}

	public void onPage(PageEvent page) {
		this.getOrders(page.getPage() + 1);
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
}
