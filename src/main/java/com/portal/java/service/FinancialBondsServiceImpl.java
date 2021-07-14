package com.portal.java.service;

import java.io.Serializable;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;

import com.portal.java.dto.FinancialBondsPageDTO;
import com.portal.java.repository.FinancialBondsRepository;

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
	public Optional<FinancialBondsPageDTO> find(int page, int pageSize)
			throws SocketTimeoutException, ConnectException, TimeoutException, SocketException {
		try {
			return Optional.of(repository.find(page, pageSize));
		} catch (NotFoundException e) {
			return Optional.empty();
		}
	}

	@Override
	public Optional<FinancialBondsPageDTO> findByCustomerName(int page, int pageSize, String name)
			throws SocketTimeoutException, ConnectException, TimeoutException, SocketException {
		try {
			return Optional.of(repository.findByCustomerName(page, pageSize, name));
		} catch (NotFoundException e) {
			return Optional.empty();
		}
	}

}
