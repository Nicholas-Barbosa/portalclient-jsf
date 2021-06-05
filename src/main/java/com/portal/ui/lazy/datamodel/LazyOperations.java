package com.portal.ui.lazy.datamodel;

import java.util.Collection;

public interface LazyOperations<T> {

	void addCollection(Collection<T> list);

	void clearCollection();

	Collection<T> getCollection();

	void turnCollectionElegibleToGB();

}
