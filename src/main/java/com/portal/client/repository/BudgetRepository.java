package com.portal.client.repository;

import java.io.Serializable;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import com.portal.client.dto.BaseBudget;
import com.portal.client.dto.BudgetFullProjection;
import com.portal.client.dto.BudgetPage;
import com.portal.client.dto.BudgetToSaveJsonSerializable;

public interface BudgetRepository extends Serializable {

	BudgetPage findAll(int page, int pageSize)
			throws SocketTimeoutException, ConnectException, SocketException, TimeoutException;

	BaseBudget save(BudgetToSaveJsonSerializable request)
			throws SocketTimeoutException, ConnectException, SocketException, TimeoutException;

	Optional<BudgetFullProjection> findByCode(String code)
			throws SocketTimeoutException, ConnectException, SocketException, TimeoutException;

}
