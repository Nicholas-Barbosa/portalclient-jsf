package com.farawaybr.portal.repository;

import java.util.Optional;

import com.farawaybr.portal.dto.OpenPaymentsPage;

public interface OpenPaymentRepository {

	Optional<OpenPaymentsPage> find(int page, int pageSize);

	Optional<OpenPaymentsPage> findByCustomerCodeStore(int page, int pageSize, String code, String store);
}
