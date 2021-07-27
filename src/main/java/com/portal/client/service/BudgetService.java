package com.portal.client.service;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import com.portal.client.dto.BudgetFullProjection;
import com.portal.client.dto.BudgetPage;
import com.portal.client.dto.BudgetSavedResponse;
import com.portal.client.dto.BudgetToSave;
import com.portal.client.dto.CustomerRepresentativeOrderForm;

public interface BudgetService extends ServiceSerializable {

	BudgetPage findAll(int page, int pageSize)
			throws SocketTimeoutException, ConnectException, SocketException, TimeoutException;

	BudgetSavedResponse save(BudgetToSave request, CustomerRepresentativeOrderForm ordersForm)
			throws SocketTimeoutException, ConnectException, SocketException, TimeoutException;

	Optional<BudgetFullProjection> findByCode(String code)
			throws SocketTimeoutException, ConnectException, SocketException, TimeoutException;

	void checkBudgetState(BudgetToSave toCheck);
}
