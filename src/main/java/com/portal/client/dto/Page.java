package com.portal.client.dto;

import java.io.Serializable;
import java.util.Collection;

public interface Page<T> extends Serializable {

	int getPageSize();

	int getPage();

	int totalPages();

	int totalItems();
	
	Collection<T> getContent();
}
