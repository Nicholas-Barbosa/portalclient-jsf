package com.portal.client.ui.lazy.datamodel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

import com.portal.client.vo.Product;

public class ProductLazyDataModel extends LazyBehaviorDataModel<Product> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2915731141483345370L;

	private List<Product> products;

	public ProductLazyDataModel() {
		this.products = new ArrayList<>();
	}

	@Override
	public List<Product> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
		// TODO Auto-generated method stub
		return products;
	}

	@Override
	public String getRowKey(Product object) {
		// TODO Auto-generated method stub
		return object.getCommercialCode();
	}

	@Override
	public Product getRowData(String rowKey) {
		return products.parallelStream().filter((Product p) -> p.getCommercialCode().equals(rowKey)).findAny()
				.orElse(null);
	}

	@Override
	public void addCollection(Collection<Product> list) {
		products = new ArrayList<>(list);

	}

	@Override
	public void clearCollection() {
		products.clear();

	}

	@Override
	public Collection<Product> getCollection() {
		// TODO Auto-generated method stub
		return new ArrayList<>(products);
	}

	@Override
	public void turnCollectionElegibleToGB() {
		this.products = null;

	}

	@Override
	public boolean removeObject(Product t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeObjects(List<Product> t) {
		// TODO Auto-generated method stub
		return false;
	}

}
