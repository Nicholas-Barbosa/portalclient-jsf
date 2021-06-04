package com.portal.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.portal.dto.FinancialTitlePageDTO;
import com.portal.repository.FinancialTitleRepository;

@ApplicationScoped
public class FinancialTitleServiceImpl implements FinancialTitleService {

	@Inject
	private FinancialTitleRepository repository;

	@Override
	public FinancialTitlePageDTO find(int page, int pageSize) {
		return repository.find(page, pageSize);
	}

}
