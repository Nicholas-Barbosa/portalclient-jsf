package com.portal.java.service;

import java.io.Serializable;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.portal.java.dto.FinancialBondsPageDTO;
import com.portal.java.repository.FinancialBondsRepository;

@ApplicationScoped
public class FinancialBondsServiceImpl implements FinancialBondsService,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8498999751949468744L;
	@Inject
	private FinancialBondsRepository repository;

	@Override
	public FinancialBondsPageDTO find(int page, int pageSize)throws SocketTimeoutException, ConnectException, TimeoutException,SocketException {
		return repository.find(page, pageSize);
	}

}
