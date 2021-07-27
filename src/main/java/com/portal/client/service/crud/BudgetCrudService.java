package com.portal.client.service.crud;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import com.portal.client.dto.BaseBudget;
import com.portal.client.dto.BudgetFullProjection;
import com.portal.client.dto.BudgetPage;
import com.portal.client.dto.CustomerRepresentativeOrderForm;
import com.portal.client.service.ServiceSerializable;

public interface BudgetCrudService extends ServiceSerializable, CrudService {

	BudgetPage findAll(int page, int pageSize)
			throws SocketTimeoutException, ConnectException, SocketException, TimeoutException;

	BaseBudget save(BaseBudget budget, CustomerRepresentativeOrderForm ordersForm)
			throws SocketTimeoutException, ConnectException, SocketException, TimeoutException;

	Optional<BudgetFullProjection> findByCode(String code)
			throws SocketTimeoutException, ConnectException, SocketException, TimeoutException;

	void checkBudgetState(BaseBudget toCheck);
}
