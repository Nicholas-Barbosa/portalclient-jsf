package com.portal.java.ui.lazy.datamodel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import com.portal.java.dto.Customer;

public class CustomerLazyDataModel extends LazyDataModel<Customer> implements LazyOperations<Customer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8941653163666281143L;

	private List<Customer> customers;

	public CustomerLazyDataModel() {
		this.customers = new ArrayList<>();
	}

	public CustomerLazyDataModel(List<Customer> customers) {
		super();
		this.customers = customers;
	}

	@Override
	public List<Customer> load(int first, int pageSize, Map<String, SortMeta> sortBy,
			Map<String, FilterMeta> filterBy) {
		return customers;
	}

	@Override
	public String getRowKey(Customer object) {
		// TODO Auto-generated method stub
		return object.getCgc();
	}

	@Override
	public Customer getRowData(String rowKey) {
		return customers.parallelStream().filter((Customer x) -> x.getCgc().equals(rowKey)).findAny().orElse(null);
	}

	@Override
	public void addCollection(Collection<Customer> list) {
		this.customers = new ArrayList<>(list);

	}

	@Override
	public void clearCollection() {
		this.customers.clear();

	}

	@Override
	public Collection<Customer> getCollection() {
		return this.customers;
	}

	@Override
	public void turnCollectionElegibleToGB() {
		this.customers = null;

	}

}
