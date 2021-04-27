package com.portal.ui.lazy.datamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import com.portal.dto.CustomerGaussDTO;

public class CustomerGaussDTOLazyDataModel extends LazyDataModel<CustomerGaussDTO>
		implements LazyOperations<CustomerGaussDTO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8941653163666281143L;

	private  List<CustomerGaussDTO> customers;

	public CustomerGaussDTOLazyDataModel() {
		this.customers = new ArrayList<>();
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

	@Override
	public CustomerGaussDTO getRowData(String rowKey) {
		return customers.parallelStream().filter((CustomerGaussDTO x) -> x.getCgc().equals(rowKey)).findAny()
				.orElse(null);
	}

	@Override
	public void addCollection(List<CustomerGaussDTO> list) {

		this.customers = new ArrayList<>(list);

	}

	@Override
	public void clearCollection() {
		this.customers.clear();

	}

	@Override
	public List<CustomerGaussDTO> getCollection() {
		return this.customers == null ? new ArrayList<>() : customers;
	}
}
