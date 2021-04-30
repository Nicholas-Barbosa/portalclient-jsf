package com.portal.ui.lazy.datamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import com.portal.dto.ProductGaussDTO;

public class ProductGaussDTOLazyDataModel extends LazyDataModel<ProductGaussDTO>
		implements LazyOperations<ProductGaussDTO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2915731141483345370L;

	private List<ProductGaussDTO> products;

	public ProductGaussDTOLazyDataModel() {
		this.products = new ArrayList<>();
	}

	@Override
	public List<ProductGaussDTO> load(int first, int pageSize, Map<String, SortMeta> sortBy,
			Map<String, FilterMeta> filterBy) {
		// TODO Auto-generated method stub
		return products;
	}

	@Override
	public String getRowKey(ProductGaussDTO object) {
		// TODO Auto-generated method stub
		return object.getCommercialCode();
	}

	@Override
	public ProductGaussDTO getRowData(String rowKey) {
		return products.parallelStream().filter((ProductGaussDTO p) -> p.getCommercialCode().equals(rowKey)).findAny()
				.orElse(null);
	}

	public void addProducts(List<ProductGaussDTO> products) {
		this.products = new ArrayList<>(products);
	}

	@Override
	public void addCollection(List<ProductGaussDTO> list) {
		products = new ArrayList<>(list);

	}

	@Override
	public void clearCollection() {
		products.clear();

	}

	@Override
	public List<ProductGaussDTO> getCollection() {
		// TODO Auto-generated method stub
		return new ArrayList<>(products);
	}

}
