package com.portal.controller.lazycollection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import com.portal.dto.CustomerGaussDTO;

public class CustomerGaussDTOLazyDataModel extends LazyDataModel<CustomerGaussDTO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8941653163666281143L;

	private List<CustomerGaussDTO> customers;

	public CustomerGaussDTOLazyDataModel() {
		// TODO Auto-generated constructor stub
	}

	public CustomerGaussDTOLazyDataModel(List<CustomerGaussDTO> customers) {
		super();
		this.customers = customers;
	}

	@Override
	public List<CustomerGaussDTO> load(int first, int pageSize, Map<String, SortMeta> sortBy,
			Map<String, FilterMeta> filterBy) {
		return customers;
	}

	@Override
	public String getRowKey(CustomerGaussDTO object) {
		// TODO Auto-generated method stub
		return object.getCgc();
	}

	public void addCollectionToLazyCustomers(List<CustomerGaussDTO> customers) {
		this.customers = new ArrayList<CustomerGaussDTO>(customers);
	}

	public void clearCustomers() {
		this.customers.clear();
	}

	public List<CustomerGaussDTO> getCustomers() {
		return customers == null ? new ArrayList<>() : new ArrayList<>(customers);
	}
}
