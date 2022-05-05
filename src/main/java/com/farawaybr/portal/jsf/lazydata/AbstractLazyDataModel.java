package com.farawaybr.portal.jsf.lazydata;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

public abstract class AbstractLazyDataModel<T> extends LazyDataModel<T> implements LazyBehavior<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7142768819768641175L;

	private List<T> collection;

	@Override
	public List<T> getCollection() {
		// TODO Auto-generated method stub
		return collection;
	}

	@Override
	public void setCollection(Collection<T> list) {
		this.collection = new ArrayList<T>(list);

	}

	@Override
	public void clearCollection() {
		collection.clear();
	}

	@Override
	public void turnCollectionElegibleToGB() {
		collection = null;
	}

	@Override
	public boolean removeObject(T t) {
		// TODO Auto-generated method stub
		return collection.remove(t);
	}

	@Override
	public boolean removeObjects(List<T> t) {
		return collection.removeAll(t);
	}

	@Override
	public List<T> load(int arg0, int arg1, Map<String, SortMeta> arg2, Map<String, FilterMeta> arg3) {
		// TODO Auto-generated method stub
		return collection;
	}

	@Override
	public int count(Map<String, FilterMeta> arg0) {
		// TODO Auto-generated method stub
		return collection != null ? collection.size() : 0;
	}

	@Override
	public T getRowData(String rowKey) {
		// TODO Auto-generated method stub
		return collection.parallelStream().filter(i -> this.getRowKey(i).equals(rowKey)).findAny().orElse(null);
	}
}
