package com.farawaybr.portal.service.crud;

import java.io.Serializable;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.farawaybr.portal.dto.OpenPaymentsPage;
import com.farawaybr.portal.repository.OpenPaymentRepository;

@ApplicationScoped
public class OpenPaymentServiceImpl implements OpenPaymentService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8498999751949468744L;

	private OpenPaymentRepository repository;

	@Inject
	public OpenPaymentServiceImpl(OpenPaymentRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public Optional<OpenPaymentsPage> find(int page, int pageSize) {
		return repository.find(page, pageSize);

	}

	@Override
	public Optional<OpenPaymentsPage> findByCustomerCodeStore(int page, int pageSize, String code, String store) {
		return repository.findByCustomerCodeStore(page, pageSize, code, store);

	}

}
