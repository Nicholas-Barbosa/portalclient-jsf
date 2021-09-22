package com.portal.client.service.crud;

import java.util.Optional;

import com.portal.client.dto.FinancialBondsPage;

public interface BillsToReceiveService {

	Optional<FinancialBondsPage> find(int page, int pageSize);

	Optional<FinancialBondsPage> findByCustomerCodeStore(int page, int pageSize, String code, String store);

}
