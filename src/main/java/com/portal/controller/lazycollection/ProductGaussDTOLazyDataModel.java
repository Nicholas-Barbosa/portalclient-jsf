package com.portal.controller.lazycollection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import com.portal.dto.ProductGaussDTO;

public class ProductGaussDTOLazyDataModel extends LazyDataModel<ProductGaussDTO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2915731141483345370L;

	private List<ProductGaussDTO> products;

	@Override
	public List<ProductGaussDTO> load(int first, int pageSize, Map<String, SortMeta> sortBy,
			Map<String, FilterMeta> filterBy) {
		// TODO Auto-generated method stub
		return products;
	}

	public void addCollectionToLazyProducts(List<ProductGaussDTO> products) {
		this.products = new ArrayList<>(products);
	}
}
