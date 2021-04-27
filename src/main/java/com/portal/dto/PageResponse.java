package com.portal.dto;

import java.util.Collection;

public interface PageResponse<T> {

	int getPageSize();

	int getPage();

	int totalPages();

	int totalItems();
	
	Collection<T> getContent();
}
