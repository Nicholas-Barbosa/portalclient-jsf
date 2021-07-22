package com.portal.client.dto;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;

import com.portal.client.vo.Customer;

public class BudgetToSaveJsonSerializable {

	@JsonbTransient
	private BudgetToSave budgetToSave;
	@JsonbTransient
	private Customer customer;
	private String representativeOrder;
	private String customerOrder;
	private Set<ItemBudgetToSaveJsonSerializable> items;

	public BudgetToSaveJsonSerializable(BudgetToSave budgetToSave, CustomerRepresentativeOrderForm ordersForm) {
		super();
		this.budgetToSave = budgetToSave;
		this.customer = budgetToSave.getCustomerOnOrder().getCustomer();
		this.items = budgetToSave.getItems().parallelStream().map(ItemBudgetToSaveJsonSerializable::new)
				.collect(ConcurrentSkipListSet::new, Set::add, Set::addAll);
		this.representativeOrder = ordersForm.getRepresentativeOrder();
		this.customerOrder = ordersForm.getCustomerOrder();
	}

	@JsonbProperty("client_code")
	public String getCustomerCode() {
		return customer.getCode();
	}

	@JsonbProperty("store")
	public String getCustomerStore() {
		return customer.getStore();
	}

	@JsonbProperty("representative_order")
	public String getRepresentativeOrder() {
		return representativeOrder;
	}

	public void setRepresentativeOrder(String representativeOrder) {
		this.representativeOrder = representativeOrder;
	}

	@JsonbProperty("client_order")
	public String getCustomerOrder() {
		return customerOrder;
	}

	public void setCustomerOrder(String customerOrder) {
		this.customerOrder = customerOrder;
	}

	@JsonbProperty("message")
	public String getMessage() {
		return budgetToSave.getCustomerOnOrder().getMessage();
	}

	@JsonbProperty("items")
	public Set<ItemBudgetToSaveJsonSerializable> getItems() {
		return items;
	}
}
