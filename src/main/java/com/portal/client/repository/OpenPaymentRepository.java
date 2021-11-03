package com.portal.client.repository;

import java.util.Optional;

import com.portal.client.dto.OpenPaymentsPage;

public interface OpenPaymentRepository {

	Optional<OpenPaymentsPage> find(int page, int pageSize);

	Optional<OpenPaymentsPage> findByCustomerCodeStore(int page, int pageSize, String code, String store);
}
