package com.portal.client.ui.lazy.datamodel;

import java.util.Collection;
import java.util.List;

import com.portal.client.vo.Page;

public class LazyPopulateUtils {

	@SuppressWarnings("unchecked")
	public static <T extends LazyDataModelBase<?>, U extends Page<?>> void populate(T lazy, U wrapperPage) {
		lazy.setPageSize(wrapperPage.getPageSize());
		lazy.setRowCount(wrapperPage.totalItems());
		Collection<?> content = wrapperPage.getContent();
		((LazyOperations<T>) lazy).addCollection((Collection<T>) content);
	}

	@SuppressWarnings("unchecked")
	public static <T extends LazyDataModelBase<?>, U> void populateSingleRow(T lazy, U wrappedObject) {
		lazy.setPageSize(1);
		lazy.setRowCount(1);
		((LazyOperations<T>) lazy).addCollection((List<T>) List.of(wrappedObject));
	}
}
