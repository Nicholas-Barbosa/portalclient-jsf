package com.portal.repository;

import com.portal.dto.FinancialTitlePageDTO;

public interface FinancialTitleRepository {

	FinancialTitlePageDTO find(int page, int pageSize);
}
