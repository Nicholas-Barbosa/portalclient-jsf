package com.farawaybr.portal.jsf.lazydata;

import java.util.Collection;
import java.util.List;

public interface LazyBehavior<T> {

	List<T> getCollection();
	
	void setCollection(Collection<T> list);

	void clearCollection();

	void turnCollectionElegibleToGB();

	boolean removeObject(T t);
	
	boolean removeObjects(List<T> t);
}
