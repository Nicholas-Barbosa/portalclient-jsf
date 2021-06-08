package com.portal.java.ui.lazy.datamodel;

import java.util.Collection;
import java.util.List;

import org.primefaces.model.LazyDataModel;

import com.portal.java.dto.Page;

public class LazyPopulateUtils {

	@SuppressWarnings("unchecked")
	public static <T extends LazyDataModel<?>, U extends Page<?>> void populate(T lazy, U wrapperPage) {
		lazy.setPageSize(wrapperPage.getPageSize());
		lazy.setRowCount(wrapperPage.totalItems());
		Collection<?> content = wrapperPage.getContent();
		((LazyOperations<T>) lazy).addCollection((Collection<T>)content);
	}

	@SuppressWarnings("unchecked")
	public static <T extends LazyDataModel<?>, U> void populateSingleRow(T lazy, U wrappedObject) {
		lazy.setPageSize(1);
		lazy.setRowCount(1);
		((LazyOperations<T>) lazy).addCollection((List<T>) List.of(wrappedObject));
	}
}
