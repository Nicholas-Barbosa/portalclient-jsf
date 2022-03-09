package com.portal.client.ui.lazy.datamodel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

import com.portal.client.dto.OrderSemiProjection;

public class OrderLazyDataModel extends LazyBehaviorDataModel<OrderSemiProjection> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5390188290403608221L;

	private List<OrderSemiProjection> orders;

	@Override
	public void addCollection(Collection<OrderSemiProjection> list) {
		// TODO Auto-generated method stub
		orders = new ArrayList<>(list);
	}

	@Override
	public void clearCollection() {
		orders.clear();
		super.setRowCount(0);
	}

	@Override
	public Collection<OrderSemiProjection> getCollection() {
		// TODO Auto-generated method stub
		return orders;
	}

	@Override
	public void turnCollectionElegibleToGB() {
		orders = null;
	}

	@Override
	public List<OrderSemiProjection> load(int first, int pageSize, Map<String, SortMeta> sortBy,
			Map<String, FilterMeta> filterBy) {
		// TODO Auto-generated method stub
		return orders;
	}

	@Override
	public boolean removeObject(OrderSemiProjection t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeObjects(List<OrderSemiProjection> t) {
		// TODO Auto-generated method stub
		return false;
	}

}
