package com.portal.client.ui.lazy.datamodel;

import java.util.Collection;
import java.util.List;

public interface LazyBehavior<T> {

	void addCollection(Collection<T> list);

	void clearCollection();

	Collection<T> getCollection();

	void turnCollectionElegibleToGB();

	boolean removeObject(T t);
	
	boolean removeObjects(List<T> t);
}
