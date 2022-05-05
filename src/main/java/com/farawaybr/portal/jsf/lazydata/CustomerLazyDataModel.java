package com.farawaybr.portal.jsf.lazydata;

import com.farawaybr.portal.vo.Customer;

public class CustomerLazyDataModel extends AbstractLazyDataModel<Customer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@Override
	public String getRowKey(Customer arg0) {
		// TODO Auto-generated method stub
		return arg0.getCnpj();
	}
}
