package com.portal.client.service.crud;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.portal.client.dto.BaseBudget;
import com.portal.client.dto.BudgetPage;
import com.portal.client.dto.CustomerRepresentativeOrderForm;
import com.portal.client.dto.ItemBudgetToEstimate;
import com.portal.client.dto.ProspectCustomerOnOrder;
import com.portal.client.exception.CustomerNotAllowed;
import com.portal.client.repository.BudgetRepository;
import com.portal.client.vo.BudgetEstimatedResultSet;

@ApplicationScoped
public class BudgetCrudServiceImpl implements BudgetCrudService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4268548772630741803L;

	private BudgetRepository budgetRepository;

	@Inject
	public BudgetCrudServiceImpl(BudgetRepository budgetRepository) {
		super();
		this.budgetRepository = budgetRepository;
	}

	@Override
	public BudgetPage findAll(int page, int pageSize)
			throws SocketTimeoutException, ConnectException, SocketException, TimeoutException {
		return budgetRepository.findAll(page, pageSize);

	}

	@Override
	public void save(BaseBudget budget, CustomerRepresentativeOrderForm ordersForm)
			throws SocketTimeoutException, ConnectException, SocketException, TimeoutException {
		checkBudgetState(budget);
		budget.setCustomerOrder(ordersForm.getCustomerOrder());
		budget.setRepresentativeOrder(ordersForm.getRepresentativeOrder());
		budgetRepository.save(budget);
	}

	@Override
	public Optional<BaseBudget> findByCode(String code)
			throws SocketTimeoutException, ConnectException, SocketException, TimeoutException {
		return budgetRepository.findByCode(code);
	}

	@Override
	public void checkBudgetState(BaseBudget budgetRequest) {
		if (budgetRequest == null)
			throw new IllegalArgumentException("parameter is null!");
		if (budgetRequest.getCustomerOnOrder() == null)
			throw new IllegalArgumentException("CustomerOnOrder is null!");
		if (budgetRequest.getItems().size() == 0)
			throw new IllegalArgumentException("No items on budget!");
		if (budgetRequest.getCustomerOnOrder() instanceof ProspectCustomerOnOrder)
			throw new CustomerNotAllowed("Save customer is only for normal customer");
	}

	@Override
	public BudgetEstimatedResultSet estimate(String customerCode, String customerStore,
			Set<ItemBudgetToEstimate> itemsToEstimate)
			throws SocketTimeoutException, ConnectException, SocketException, TimeoutException {
		return budgetRepository.estimate(customerCode, customerStore, itemsToEstimate);
	}

}
