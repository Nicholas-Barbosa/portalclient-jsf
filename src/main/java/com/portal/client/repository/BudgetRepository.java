package com.portal.client.repository;

import java.io.Serializable;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import com.portal.client.dto.BaseBudget;
import com.portal.client.dto.BudgetPage;
import com.portal.client.dto.ItemBudgetToEstimate;
import com.portal.client.vo.BudgetEstimatedResultSet;

public interface BudgetRepository extends Serializable {

	BudgetPage findAll(int page, int pageSize)
			throws SocketTimeoutException, ConnectException, SocketException, TimeoutException;

	void save(BaseBudget request)
			throws SocketTimeoutException, ConnectException, SocketException, TimeoutException;

	Optional<BaseBudget> findByCode(String code)
			throws SocketTimeoutException, ConnectException, SocketException, TimeoutException;

	BudgetEstimatedResultSet estimate(String customerCode, String customerStore, Set<ItemBudgetToEstimate> items)
			throws SocketTimeoutException, ConnectException, SocketException, TimeoutException;
}
