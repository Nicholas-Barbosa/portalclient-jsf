package com.portal.ui.lazy.datamodel;

import java.util.List;

public interface LazyOperations<T> {

	void addCollection(List<T> list);
	void clearCollection();
	List<T> getCollection();
}
