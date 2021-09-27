package com.portal.client.ui.lazy.datamodel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

import com.portal.client.vo.Item;

public class ItemLazyDataModel extends LazyBehaviorDataModel<Item> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4283482853730481427L;

	private List<Item> items;

	@Override
	public void addCollection(Collection<Item> list) {
		items = new ArrayList<>(list);
	}

	@Override
	public void clearCollection() {
		items.clear();

	}

	@Override
	public Collection<Item> getCollection() {
		// TODO Auto-generated method stub
		return items;
	}

	@Override
	public void turnCollectionElegibleToGB() {
		items = null;

	}

	@Override
	public List<Item> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
		// TODO Auto-generated method stub
		return items;
	}

	@Override
	public Item getRowData(String rowKey) {
		// TODO Auto-generated method stub
		return items.parallelStream().filter(i -> this.getRowKey(i).equals(rowKey)).findAny().orElse(null);
	}

	@Override
	public String getRowKey(Item object) {
		return object.getProduct().getCommercialCode();
	}

	@Override
	public boolean removeObject(Item t) {
		// TODO Auto-generated method stub
		return items.remove(t);
	}

	@Override
	public boolean removeObjects(List<Item> t) {
		return items.removeAll(t);

	}

}
