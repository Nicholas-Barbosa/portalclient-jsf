package com.portal.client.service.crud;

import java.io.Serializable;
import java.util.Optional;

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
	public Optional<FinancialBondsPage> find(int page, int pageSize) {
		try {
			return Optional.of(repository.find(page, pageSize));
		} catch (NotFoundException e) {
			return Optional.empty();
		}
	}

	@Override
	public Optional<FinancialBondsPage> findByCustomerCodeStore(int page, int pageSize, String code, String store) {
		try {
			return Optional.of(repository.findByCustomerCodeStore(page, pageSize, code, store));
		} catch (NotFoundException e) {
			return Optional.empty();
		}
	}

}
