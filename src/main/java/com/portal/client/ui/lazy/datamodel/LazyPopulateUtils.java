package com.portal.client.ui.lazy.datamodel;

import java.util.Collection;
import java.util.List;

import com.portal.client.dto.Page;

public class LazyPopulateUtils {

	@SuppressWarnings("unchecked")
	public static <T extends LazyBehaviorDataModel<?>, U extends Page<?>> void populate(T lazy, U wrapperPage) {
		lazy.setPageSize(wrapperPage.getPageSize());
		lazy.setRowCount(wrapperPage.totalItems());
		Collection<?> content = wrapperPage.getContent();
		((LazyBehavior<T>) lazy).addCollection((Collection<T>) content);
	}

	@SuppressWarnings("unchecked")
	public static <T extends LazyBehaviorDataModel<?>, U> void populateSingleRow(T lazy, U wrappedObject) {
		lazy.setPageSize(1);
		lazy.setRowCount(1);
		((LazyBehavior<T>) lazy).addCollection((List<T>) List.of(wrappedObject));
	}
}
