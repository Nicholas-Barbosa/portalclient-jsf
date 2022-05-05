package com.farawaybr.portal.jsf.controller.components;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;

@SessionScoped
public class OpenPaymentsComponentData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String code, store;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
	}
}
