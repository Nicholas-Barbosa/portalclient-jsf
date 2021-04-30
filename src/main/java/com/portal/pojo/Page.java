package com.portal.pojo;

import java.util.Collection;

public interface Page<T> {

	int getPageSize();

	int getPage();

	int totalPages();

	int totalItems();
	
	Collection<T> getContent();
}
