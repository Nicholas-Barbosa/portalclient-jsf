package com.portal.client.repository;

import java.io.Serializable;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import com.portal.client.dto.BudgetResponse;
import com.portal.client.dto.BudgetToSaveJsonSerializable;
import com.portal.client.vo.BudgetPage;

public interface BudgetRepository extends Serializable {

	BudgetPage findAll(int page, int pageSize)
			throws SocketTimeoutException, ConnectException, SocketException, TimeoutException;

	BudgetResponse save(BudgetToSaveJsonSerializable request)
			throws SocketTimeoutException, ConnectException, SocketException, TimeoutException;
}
