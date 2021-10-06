package com.portal.client.controller;

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

import com.portal.client.dto.Customer;
import com.portal.client.dto.CustomerOnOrder;
import com.portal.client.dto.ProspectCustomerOnOrder;
import com.portal.client.exception.ItemQuantityNotAllowed;
import com.portal.client.repository.OrderBadRequestExcpetion;
import com.portal.client.service.OrderCommonBehaviorHelper;
import com.portal.client.service.OrderItemQuantityCalculator;
import com.portal.client.service.crud.OrderCrudService;
import com.portal.client.util.jsf.FacesUtils;
import com.portal.client.vo.Item;
import com.portal.client.vo.Order;
import com.portal.client.vo.Product;

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
	private OrderCommonBehaviorHelper orderHelper;
	@Inject
	private OrderCrudService orderCrudService;
	@Inject
	private HttpSession session;
	@Inject
	private OrderItemQuantityCalculator ordemQuantityCalculator;

	@Inject
	private DtableItemController dtItemsController;

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
			FacesUtils.error(null, "O servidor retornou um erro", e.getError().getErrorMessage(), "growl");
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

	public void handleProductResult(SelectEvent<Optional<Product>> event) {
		event.getObject().ifPresentOrElse(p -> {
			orderHelper.addItem(order, Item.product(p));
			FacesUtils.ajaxUpdate("formItems:dtItems", "frmTotals");
		}, () -> FacesUtils.warn(null, "Produto não selecionado", "Operação cancelada", "growl"));

	}

	public void handleCustomerResult(SelectEvent<Optional<Customer>> event) {
		event.getObject().ifPresentOrElse(c -> {
			orderHelper.setCustomer(order, new CustomerOnOrder(c));
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

	public DtableItemController getDtItemsController() {
		return dtItemsController;
	}
}
