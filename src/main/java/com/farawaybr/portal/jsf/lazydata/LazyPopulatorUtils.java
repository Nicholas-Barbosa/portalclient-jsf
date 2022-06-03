package com.farawaybr.portal.jsf.lazydata;

import java.util.Collection;
import java.util.List;

import com.farawaybr.portal.vo.Page;

public class LazyPopulatorUtils {

	/**
	 * 
	 * @param <T>         LazyBehaviorDataModel covariante. Type that will be used
	 *                    as list in p:dataTable.
	 * @param <U>         Page covariante.
	 * @param lazy
	 * @param wrapperPage
	 */
	@SuppressWarnings("unchecked")
	public static <T extends AbstractLazyDataModel<?>, U extends Page<?>> void populate(T lazy, U wrapperPage) {
		lazy.setPageSize(wrapperPage.getPageSize());
		lazy.setVrowCount(wrapperPage.totalItems());
		Collection<?> content = wrapperPage.getContent();
		((LazyBehavior<T>) lazy).setCollection((Collection<T>) content);
	}

	@SuppressWarnings("unchecked")
	public static <T extends AbstractLazyDataModel<?>, P extends Page<?>, C> void populate(T lazy, P page, C content) {
		lazy.setPageSize(page.getPageSize());
		lazy.setVrowCount(page.totalItems());
		((LazyBehavior<T>) lazy).setCollection((Collection<T>) content);
	}

	@SuppressWarnings("unchecked")
	public static <T extends AbstractLazyDataModel<?>, U> void populateSingleRow(T lazy, U wrappedObject) {
		lazy.setPageSize(1);
		lazy.setRowCount(1);
		((LazyBehavior<T>) lazy).setCollection((List<T>) List.of(wrappedObject));
	}
}
