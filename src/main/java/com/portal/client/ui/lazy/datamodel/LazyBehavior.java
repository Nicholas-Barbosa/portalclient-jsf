package com.portal.client.ui.lazy.datamodel;

import java.util.Collection;

public interface LazyBehavior<T> {

	void addCollection(Collection<T> list);

	void clearCollection();

	Collection<T> getCollection();

	void turnCollectionElegibleToGB();

}
