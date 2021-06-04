package com.portal.service;

import com.portal.dto.FinancialTitlePageDTO;

public interface FinancialTitleService {

	FinancialTitlePageDTO find(int page, int pageSize);
	
}
