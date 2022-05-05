package com.farawaybr.portal.service.crud;

import java.util.Optional;

import com.farawaybr.portal.dto.OpenPaymentsPage;

public interface OpenPaymentService {

	Optional<OpenPaymentsPage> find(int page, int pageSize);

	Optional<OpenPaymentsPage> findByCustomerCodeStore(int page, int pageSize, String code, String store);

}
