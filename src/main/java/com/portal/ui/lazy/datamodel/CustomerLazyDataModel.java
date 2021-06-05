package com.portal.ui.lazy.datamodel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import com.portal.dto.CustomerDTO;

public class CustomerLazyDataModel extends LazyDataModel<CustomerDTO> implements LazyOperations<CustomerDTO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8941653163666281143L;

	private List<CustomerDTO> customers;

	public CustomerLazyDataModel() {
		this.customers = new ArrayList<>();
	}

	public CustomerLazyDataModel(List<CustomerDTO> customers) {
		super();
		this.customers = customers;
	}

	@Override
	public List<CustomerDTO> load(int first, int pageSize, Map<String, SortMeta> sortBy,
			Map<String, FilterMeta> filterBy) {
		return customers;
	}

	@Override
	public String getRowKey(CustomerDTO object) {
		// TODO Auto-generated method stub
		return object.getCgc();
	}

	@Override
	public CustomerDTO getRowData(String rowKey) {
		return customers.parallelStream().filter((CustomerDTO x) -> x.getCgc().equals(rowKey)).findAny().orElse(null);
	}

	@Override
	public void addCollection(Collection<CustomerDTO> list) {
		this.customers = new ArrayList<>(list);

	}

	@Override
	public void clearCollection() {
		this.customers.clear();

	}

	@Override
	public Collection<CustomerDTO> getCollection() {
		return this.customers;
	}

	@Override
	public void turnCollectionElegibleToGB() {
		this.customers = null;

	}

}
