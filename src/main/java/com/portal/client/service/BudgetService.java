package com.portal.client.service;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import com.portal.client.dto.BudgetToSave;
import com.portal.client.dto.CustomerRepresentativeOrderForm;
import com.portal.client.dto.BudgetResponse;
import com.portal.client.vo.BudgetPage;

public interface BudgetService extends ServiceSerializable {

	BudgetPage findAll(int page, int pageSize)
			throws SocketTimeoutException, ConnectException, SocketException, TimeoutException;

	BudgetResponse save(BudgetToSave request, CustomerRepresentativeOrderForm ordersForm)
			throws SocketTimeoutException, ConnectException, SocketException, TimeoutException;

	void checkBudgetState(BudgetToSave toCheck);
}
