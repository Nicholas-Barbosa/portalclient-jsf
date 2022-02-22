package com.portal.client.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.PrimeFaces;

import com.portal.client.dto.ItemLineDiscountForm;
import com.portal.client.exception.CustomerNotAllowed;
import com.portal.client.util.jsf.FacesUtils;
import com.portal.client.vo.Item;
import com.portal.client.vo.Order;
import com.portal.client.vo.Product;

@ViewScoped
@Named
public class OrderDiscountController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7518415601460334220L;
	@Inject
	private HttpSession httpSession;

	private Set<String> lines;
	private Order order;
	private int activeTab;
	private ItemLineDiscountForm lineDiscountForm;
	private BigDecimal gloablDiscount;

	public OrderDiscountController() {
		lineDiscountForm = new ItemLineDiscountForm();
	}

	public void applyGlobalDiscount() {
		try {
			// orderHelper.setDiscount(order, gloablDiscount);
			this.removeFromSession();
			closeDialog();
		} catch (CustomerNotAllowed e) {
			FacesUtils.fatal(null, "Cliente não autorizado", null);
			PrimeFaces.current().ajax().update("growl");
		}
	}

	public void applyLineDiscount() {
		this.removeFromSession();
		// orderHelper.lineDiscount(order, lineDiscountForm);
		closeDialog();
	}

	public void getOrderFromSession() {
		order = (Order) httpSession.getAttribute("order-todisc");
	}

	private void removeFromSession() {
		httpSession.removeAttribute("order-todisc");
	}

	public void loadCurrentItemLines() {
		if (activeTab == 1 && lines == null)
			this.lines = order.getItems().parallelStream().map(Product::getLine)
					.collect(Collectors.toSet());
	}

	private void closeDialog() {
		PrimeFaces.current().dialog().closeDynamic(null);
	}

	public Set<String> getLines() {
		return lines;
	}

	public Order getOrder() {
		return order;
	}

	public int getActiveTab() {
		return activeTab;
	}

	public void setActiveTab(int activeTab) {
		this.activeTab = activeTab;
	}

	public ItemLineDiscountForm getLineDiscountForm() {
		return lineDiscountForm;
	}

	public BigDecimal getGloablDiscount() {
		return gloablDiscount;
	}

	public void setGloablDiscount(BigDecimal gloablDiscount) {
		this.gloablDiscount = gloablDiscount;
	}
}
