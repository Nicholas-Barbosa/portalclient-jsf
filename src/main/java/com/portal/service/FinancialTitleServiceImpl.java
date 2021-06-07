package com.portal.service;

import java.io.Serializable;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.portal.dto.FinancialTitlePageDTO;
import com.portal.repository.FinancialTitleRepository;

@ApplicationScoped
public class FinancialTitleServiceImpl implements FinancialTitleService,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8498999751949468744L;
	@Inject
	private FinancialTitleRepository repository;

	@Override
	public FinancialTitlePageDTO find(int page, int pageSize)throws SocketTimeoutException, ConnectException, TimeoutException {
		return repository.find(page, pageSize);
	}

}
