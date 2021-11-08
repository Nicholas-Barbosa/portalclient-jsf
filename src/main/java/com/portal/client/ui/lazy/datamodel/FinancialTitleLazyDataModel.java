package com.portal.client.ui.lazy.datamodel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

import com.portal.client.dto.OpenPaymentsPage.OpenPaymentDto;

public class FinancialTitleLazyDataModel extends LazyBehaviorDataModel<OpenPaymentDto> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8708926972142151243L;

	private List<OpenPaymentDto> titles;

	@Override
	public List<OpenPaymentDto> load(int first, int pageSize, Map<String, SortMeta> sortBy,
			Map<String, FilterMeta> filterBy) {
		return titles;
	}

	@Override
	public void addCollection(Collection<OpenPaymentDto> list) {
		this.titles = new ArrayList<>(list);
	}

	@Override
	public void clearCollection() {
		titles.clear();

	}

	@Override
	public Collection<OpenPaymentDto> getCollection() {
		return titles;
	}

	@Override
	public void turnCollectionElegibleToGB() {
		titles = null;

	}

	@Override
	public boolean removeObject(OpenPaymentDto t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeObjects(List<OpenPaymentDto> t) {
		// TODO Auto-generated method stub
		return false;
	}

}
