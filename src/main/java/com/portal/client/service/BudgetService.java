package com.portal.client.service;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import com.portal.client.dto.BudgetRequest;
import com.portal.client.dto.BudgetResponse;
import com.portal.client.vo.BudgetPage;

public interface BudgetService extends ServiceSerializable {

	BudgetPage findAll(int page, int pageSize)
			throws SocketTimeoutException, ConnectException, SocketException, TimeoutException;

	BudgetResponse save(BudgetRequest request);
}
