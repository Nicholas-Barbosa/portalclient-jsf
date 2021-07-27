package com.portal.client.service;

import java.io.Serializable;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;

import com.portal.client.dto.FinancialBondsPage;
import com.portal.client.repository.FinancialBondsRepository;

@ApplicationScoped
public class FinancialBondsServiceImpl implements FinancialBondsService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8498999751949468744L;

	private FinancialBondsRepository repository;

	@Inject
	public FinancialBondsServiceImpl(FinancialBondsRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public Optional<FinancialBondsPage> find(int page, int pageSize)
			throws SocketTimeoutException, ConnectException, TimeoutException, SocketException {
		try {
			return Optional.of(repository.find(page, pageSize));
		} catch (NotFoundException e) {
			return Optional.empty();
		}
	}

	@Override
	public Optional<FinancialBondsPage> findByCustomerCodeStore(int page, int pageSize, String code, String store)
			throws SocketTimeoutException, ConnectException, TimeoutException, SocketException {
		try {
			return Optional.of(repository.findByCustomerCodeStore(page, pageSize, code, store));
		} catch (NotFoundException e) {
			return Optional.empty();
		}
	}

}
