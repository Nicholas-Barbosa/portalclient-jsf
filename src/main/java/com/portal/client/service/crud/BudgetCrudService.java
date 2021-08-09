package com.portal.client.service.crud;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import com.portal.client.dto.BaseBudget;
import com.portal.client.dto.BudgetPage;
import com.portal.client.dto.CustomerRepresentativeOrderForm;
import com.portal.client.dto.ItemBudgetToEstimate;
import com.portal.client.service.ServiceSerializable;
import com.portal.client.vo.BudgetEstimatedResultSet;

public interface BudgetCrudService extends ServiceSerializable, CrudService {

	BudgetPage findAll(int page, int pageSize)
			throws SocketTimeoutException, ConnectException, SocketException, TimeoutException;

	void save(BaseBudget budget, CustomerRepresentativeOrderForm ordersForm)
			throws SocketTimeoutException, ConnectException, SocketException, TimeoutException;

	Optional<BaseBudget> findByCode(String code)
			throws SocketTimeoutException, ConnectException, SocketException, TimeoutException;

	void checkBudgetState(BaseBudget toCheck);

	BudgetEstimatedResultSet estimate(String customerCode, String customerStore,
			Set<ItemBudgetToEstimate> itemsToEstimate)
			throws SocketTimeoutException, ConnectException, SocketException, TimeoutException;
}
