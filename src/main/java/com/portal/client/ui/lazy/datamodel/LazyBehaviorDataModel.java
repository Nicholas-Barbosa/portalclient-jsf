package com.portal.client.ui.lazy.datamodel;

import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;

public abstract class LazyBehaviorDataModel<T> extends LazyDataModel<T> implements LazyBehavior<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7142768819768641175L;

	@Override
	public int count(Map<String, FilterMeta> arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
}
