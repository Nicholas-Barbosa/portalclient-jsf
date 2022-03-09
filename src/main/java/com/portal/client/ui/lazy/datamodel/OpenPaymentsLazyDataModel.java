package com.portal.client.ui.lazy.datamodel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

import com.portal.client.dto.OpenPaymentsPage.OpenPaymentDto;
import com.portal.client.vo.Customer;

public class OpenPaymentsLazyDataModel extends LazyBehaviorDataModel<OpenPaymentDto> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8941653163666281143L;

	private List<OpenPaymentDto> payments;

	public OpenPaymentsLazyDataModel() {
		this.payments = new ArrayList<>();
	}

	public OpenPaymentsLazyDataModel(List<OpenPaymentDto> customers) {
		super();
		this.payments = customers;
	}

	@Override
	public List<OpenPaymentDto> load(int first, int pageSize, Map<String, SortMeta> sortBy,
			Map<String, FilterMeta> filterBy) {
		return payments;
	}

	@Override
	public String getRowKey(OpenPaymentDto object) {
		// TODO Auto-generated method stub
		return object.getDocNumber();
	}

	@Override
	public OpenPaymentDto getRowData(String rowKey) {
		return payments.parallelStream().filter((OpenPaymentDto x) -> x.getDocNumber().equals(rowKey)).findAny()
				.orElse(null);
	}

	@Override
	public void addCollection(Collection<OpenPaymentDto> list) {
		this.payments = new ArrayList<>(list);

	}

	@Override
	public void clearCollection() {
		this.payments.clear();

	}

	@Override
	public Collection<OpenPaymentDto> getCollection() {
		return this.payments;
	}

	@Override
	public void turnCollectionElegibleToGB() {
		this.payments = null;

	}

	@Override
	public boolean removeObject(OpenPaymentDto t) {
		// TODO Auto-generated method stub
		return payments.remove(t);
	}

	@Override
	public boolean removeObjects(List<OpenPaymentDto> t) {
		// TODO Auto-generated method stub
		return false;
	}

}
