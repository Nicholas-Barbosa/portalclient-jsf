package com.portal.client.ui.lazy.datamodel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

import com.portal.client.dto.FinancialBondsPageDTO.FinacialBondsDTO;

public class FinancialTitleLazyDataModel extends LazyDataModelBase<FinacialBondsDTO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8708926972142151243L;

	private List<FinacialBondsDTO> titles;

	@Override
	public List<FinacialBondsDTO> load(int first, int pageSize, Map<String, SortMeta> sortBy,
			Map<String, FilterMeta> filterBy) {
		return titles;
	}

	@Override
	public void addCollection(Collection<FinacialBondsDTO> list) {
		this.titles = new ArrayList<>(list);
	}

	@Override
	public void clearCollection() {
		titles.clear();

	}

	@Override
	public Collection<FinacialBondsDTO> getCollection() {
		return titles;
	}

	@Override
	public void turnCollectionElegibleToGB() {
		titles = null;

	}

}
