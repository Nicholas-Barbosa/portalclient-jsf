package com.portal.ui.lazy.datamodel;

import java.util.List;

import org.primefaces.model.LazyDataModel;

import com.portal.pojo.Page;

public class LazyPopulateUtils {

	@SuppressWarnings("unchecked")
	public static <T extends LazyDataModel<?>, U extends Page<?>> void populate(T lazy, U wrapperPage) {
		lazy.setPageSize(wrapperPage.getPageSize());
		lazy.setRowCount(wrapperPage.totalItems());
		((LazyOperations<T>) lazy).addCollection((List<T>) wrapperPage.getContent());
	}

	@SuppressWarnings("unchecked")
	public static <T extends LazyDataModel<?>, U> void populateSingleRow(T lazy, U wrappedObject) {
		lazy.setPageSize(1);
		lazy.setRowCount(1);
		((LazyOperations<T>) lazy).addCollection((List<T>) List.of(wrappedObject));
	}
}
