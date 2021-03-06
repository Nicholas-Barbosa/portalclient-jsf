package com.farawaybr.portal.jsf.controller;

import java.io.Serializable;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;

import com.farawaybr.portal.exception.ItemQuantityNotAllowed;
import com.farawaybr.portal.jsf.controller.components.ItemOrderContainerController;
import com.farawaybr.portal.jsf.controller.show.OrderBadRequestShowController;
import com.farawaybr.portal.repository.OrderBadRequestExcpetion;
import com.farawaybr.portal.service.OrderBehaviorHelper;
import com.farawaybr.portal.service.OrderItemQuantityCalculator;
import com.farawaybr.portal.service.crud.OrderCrudService;
import com.farawaybr.portal.util.jsf.FacesUtils;
import com.farawaybr.portal.vo.Customer;
import com.farawaybr.portal.vo.Item;
import com.farawaybr.portal.vo.Order;
import com.farawaybr.portal.vo.ProspectCustomerOnOrder;

@Named
@ViewScoped
public class NewOrderController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 577560110297394236L;

	private Order order;
	private String cNameToSearch;
	@Inject
	private OrderBehaviorHelper orderHelper;
	@Inject
	private OrderCrudService orderCrudService;
	@Inject
	private HttpSession session;
	@Inject
	private OrderItemQuantityCalculator ordemQuantityCalculator;

	@Inject
	private ItemOrderContainerController dtItemsController;

	@Inject
	private OrderBadRequestShowController orderBadRequestShowController;

	private int onRowItemQuantity;

	public NewOrderController() {

	}

	@PostConstruct
	public void init() {
		newOrder();
	}

	public final void newOrder() {
		this.order = new Order();
		dtItemsController.setOrder(order);
	}

	public void onRowItemEdit(RowEditEvent<Item> event) {
		try {
			ordemQuantityCalculator.calc(order, event.getObject(), onRowItemQuantity);
		} catch (ItemQuantityNotAllowed e) {
			FacesUtils.error(null, e.getMessage(), null);
			PrimeFaces.current().ajax().update("growl");
		}
	}

	public void save() {
		try {
			orderCrudService.persist(order);
			FacesUtils.ajaxUpdate("successPersisted");
		} catch (OrderBadRequestExcpetion e) {
			orderBadRequestShowController.show(e);
		}
	}

	public void handleItemImportReturn(SelectEvent<Order> event) {
		this.orderHelper.merge(order, event.getObject());
		FacesUtils.ajaxUpdate("formItems:dtItems", "frmTotals");
	}

	public void getProspectFromSession() {
		ProspectCustomerOnOrder prosp = (ProspectCustomerOnOrder) session.getAttribute("prospect");
		orderHelper.setCustomer(order, prosp);
	}

	public void handleCustomerResult(SelectEvent<Optional<Customer>> event) {
		event.getObject().ifPresentOrElse(c -> {
			orderHelper.setCustomer(order, new com.farawaybr.portal.vo.CustomerOnOrder(c));
			FacesUtils.info(null, "Cliente selecionado", null, "growl");
			FacesUtils.ajaxUpdate("customerForm");
			FacesUtils.executeScript("PF('dlgSearchCustomer').hide();PF('blockItems').hide();");
		}, () -> FacesUtils.warn(null, "Nenhum cliente selecionado", null, "growl"));
	}

	public Order getOrder() {
		return order;
	}

	public String getcNameToSearch() {
		return cNameToSearch;
	}

	public void setcNameToSearch(String cNameToSearch) {
		this.cNameToSearch = cNameToSearch;
	}

	public int getOnRowItemQuantity() {
		return onRowItemQuantity;
	}

	public void setOnRowItemQuantity(int onRowItemQuantity) {
		this.onRowItemQuantity = onRowItemQuantity;
	}

	public ItemOrderContainerController getDtItemsController() {
		return dtItemsController;
	}

}
