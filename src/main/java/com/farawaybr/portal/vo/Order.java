package com.farawaybr.portal.vo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.farawaybr.portal.security.user.ProtheusUser;

public class Order {

	private String code, customerNumOrder,repNumOrder;
	private CustomerOnOrder customerOnOrder;
	private BigDecimal grossValue;
	private BigDecimal liquidValue;
	private BigDecimal stValue;
	private BigDecimal globalDiscount;
	private String message;
	private List<Item> items;
	private LocalDate createdAt;
	private ProtheusUser representative;
	private Invoice invoice;
	private String status;

	public Order() {
	}

	public Order(String code,String customerNumOrder, String repNumOrder, CustomerOnOrder customerOnOrder, BigDecimal grossValue,
			BigDecimal liquidValue, BigDecimal stValue, BigDecimal globalDiscount, String message, List<Item> items,
			LocalDate createdAt, ProtheusUser representative, String status) {
		super();
		this.code = code;
		this.repNumOrder = repNumOrder;
		this.customerNumOrder = customerNumOrder;
		this.customerOnOrder = customerOnOrder;
		this.grossValue = grossValue;
		this.liquidValue = liquidValue;
		this.stValue = stValue;
		this.globalDiscount = globalDiscount;
		this.message = message;
		this.items = items;
		this.createdAt = createdAt;
		this.representative = representative;
		this.status = status;
	}

	public Order(Order order) {
		this(order.code,order.customerNumOrder, order.repNumOrder, order.customerOnOrder, order.grossValue, order.liquidValue, order.stValue,
				order.globalDiscount, order.message, new ArrayList<>(order.items), order.createdAt,
				order.representative, order.getStatus());
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRepNumOrder() {
		return repNumOrder;
	}

	public void setRepNumOrder(String repNumOrder) {
		this.repNumOrder = repNumOrder;
	}
	
	public String getCustomerNumOrder() {
		return customerNumOrder;
	}
	public void setCustomerNumOrder(String customerNumOrder) {
		this.customerNumOrder = customerNumOrder;
	}

	public CustomerOnOrder getCustomerOnOrder() {
		return customerOnOrder;
	}

	public void setCustomerOnOrder(CustomerOnOrder customerOnOrder) {
		this.customerOnOrder = customerOnOrder;
	}

	public BigDecimal getGrossValue() {
		return grossValue;
	}

	public void setGrossValue(BigDecimal grossValue) {
		this.grossValue = grossValue;
	}

	public BigDecimal getLiquidValue() {
		return liquidValue;
	}

	public void setLiquidValue(BigDecimal liquidValue) {
		this.liquidValue = liquidValue;
	}

	public BigDecimal getStValue() {
		return stValue;
	}

	public void setStValue(BigDecimal stValue) {
		this.stValue = stValue;
	}

	public BigDecimal getGlobalDiscount() {
		return globalDiscount == null ? BigDecimal.ZERO : globalDiscount;
	}

	public void setGlobalDiscount(BigDecimal globalDiscount) {
		this.globalDiscount = globalDiscount;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Item> getItems() {
		return items;
	}

	public synchronized boolean addItem(Item item) {
		if (items == null) {
			items = new ArrayList<>();
		}
		if (!items.contains(item))
			return this.items.add(item);
		return false;
	}

	public boolean addItems(List<Item> items) {
		return items.stream().map(this::addItem).allMatch(x -> x == true);
	}

	public boolean removeItem(Item item) {
		return this.items.remove(item);
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}

	public ProtheusUser getRepresentative() {
		return representative;
	}

	public void setRepresentative(ProtheusUser representative) {
		this.representative = representative;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public boolean removeItems(List<Item> itemsToCompareAndRemove) {
		return items.removeAll(itemsToCompareAndRemove);
	}

	public void removeItems() {
		items.clear();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


}
