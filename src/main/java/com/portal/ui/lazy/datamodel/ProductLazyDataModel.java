package com.portal.ui.lazy.datamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import com.portal.dto.ProductQueryDTO;

public class ProductLazyDataModel extends LazyDataModel<ProductQueryDTO> implements LazyOperations<ProductQueryDTO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2915731141483345370L;

	private List<ProductQueryDTO> products;

	public ProductLazyDataModel() {
		this.products = new ArrayList<>();
	}

	@Override
	public List<ProductQueryDTO> load(int first, int pageSize, Map<String, SortMeta> sortBy,
			Map<String, FilterMeta> filterBy) {
		// TODO Auto-generated method stub
		return products;
	}

	@Override
	public String getRowKey(ProductQueryDTO object) {
		// TODO Auto-generated method stub
		return object.getCommercialCode();
	}

	@Override
	public ProductQueryDTO getRowData(String rowKey) {
		return products.parallelStream().filter((ProductQueryDTO p) -> p.getCommercialCode().equals(rowKey)).findAny()
				.orElse(null);
	}

	@Override
	public void addCollection(List<ProductQueryDTO> list) {
		products = new ArrayList<>(list);

	}

	@Override
	public void clearCollection() {
		products.clear();

	}

	@Override
	public List<ProductQueryDTO> getCollection() {
		// TODO Auto-generated method stub
		return new ArrayList<>(products);
	}

}
