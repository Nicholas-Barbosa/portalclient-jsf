package com.portal.client.service;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.portal.client.dto.BudgetRequest;
import com.portal.client.dto.BudgetResponse;
import com.portal.client.repository.BudgetRepository;
import com.portal.client.vo.BudgetPage;

@ApplicationScoped
public class BudgetServiceImpl implements BudgetService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4268548772630741803L;

	private BudgetRepository budgetRepository;

	@Inject
	public BudgetServiceImpl(BudgetRepository budgetRepository) {
		super();
		this.budgetRepository = budgetRepository;
	}

	@Override
	public BudgetPage findAll(int page, int pageSize)
			throws SocketTimeoutException, ConnectException, SocketException, TimeoutException {
		return budgetRepository.findAll(page, pageSize);

	}

	@Override
	public BudgetResponse save(BudgetRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
