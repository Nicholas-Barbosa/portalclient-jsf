package com.portal.client.service.crud;

import java.io.Serializable;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.portal.client.dto.FinancialBondsPage;
import com.portal.client.repository.BillsToReceiveRepository;

@ApplicationScoped
public class BillsToReceiveServiceImpl implements BillsToReceiveService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8498999751949468744L;

	private BillsToReceiveRepository repository;

	@Inject
	public BillsToReceiveServiceImpl(BillsToReceiveRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public Optional<FinancialBondsPage> find(int page, int pageSize) {
		return repository.find(page, pageSize);

	}

	@Override
	public Optional<FinancialBondsPage> findByCustomerCodeStore(int page, int pageSize, String code, String store) {
		return repository.findByCustomerCodeStore(page, pageSize, code, store);

	}

}
