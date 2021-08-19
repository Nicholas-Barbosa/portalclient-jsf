package com.portal.client.repository;

import com.portal.client.dto.FinancialBondsPage;

public interface FinancialBondsRepository {

	FinancialBondsPage find(int page, int pageSize);

	FinancialBondsPage findByCustomerCodeStore(int page, int pageSize, String code, String store);
}
