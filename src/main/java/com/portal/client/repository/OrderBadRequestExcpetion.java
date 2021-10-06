package com.portal.client.repository;

import com.portal.client.dto.ErrorOrcamentoAPI;

public class OrderBadRequestExcpetion extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2171512557925572574L;

	private ErrorOrcamentoAPI error;

	public OrderBadRequestExcpetion(ErrorOrcamentoAPI error) {
		super();
		this.error = error;
	}

	public ErrorOrcamentoAPI getError() {
		return error;
	}
}
