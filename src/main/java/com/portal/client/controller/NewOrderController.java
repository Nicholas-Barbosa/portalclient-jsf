package com.portal.client.controller;

import java.io.Serializable;
import java.util.Optional;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.event.SelectEvent;

import com.portal.client.dto.Customer;
import com.portal.client.dto.CustomerOnOrder;
import com.portal.client.dto.ProspectCustomerOnOrder;
import com.portal.client.service.OrderCommonBehaviorHelper;
import com.portal.client.util.jsf.FacesUtils;
import com.portal.client.vo.Order;

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
	private HttpSession session;

	public NewOrderController() {
		newOrder();
	}

	public final void newOrder() {
		this.order = new Order();
	}

	public void getProspectFromSession() {
		ProspectCustomerOnOrder prosp = (ProspectCustomerOnOrder) session.getAttribute("prospect");
		orderHelper.setCustomer(order, prosp);
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
}
