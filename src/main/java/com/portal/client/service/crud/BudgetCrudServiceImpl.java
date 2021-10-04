package com.portal.client.service.crud;

import java.util.Optional;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.portal.client.dto.BudgetFullProjection;
import com.portal.client.dto.CustomerRepresentativeOrderForm;
import com.portal.client.dto.ItemToFindPrice;
import com.portal.client.dto.ProspectCustomerOnOrder;
import com.portal.client.exception.CustomerNotAllowed;
import com.portal.client.exception.CustomerNotFoundException;
import com.portal.client.exception.ItemsNotFoundException;
import com.portal.client.repository.BudgetRepository;
import com.portal.client.vo.Budget;
import com.portal.client.vo.Page;

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
	public Page<Budget> findAll(int page, int pageSize) {
		return budgetRepository.findAll(page, pageSize);

	}

	@Override
	public void save(Budget budget, CustomerRepresentativeOrderForm ordersForm) {
		checkBudgetState(budget);
		budget.setCustomerNumOrder(ordersForm.getCustomerOrder());
		budget.setRepNumOrder(ordersForm.getRepresentativeOrder());
		budgetRepository.save(budget);
	}

	@Override
	public Optional<BudgetFullProjection> findByCode(String code) {
		return budgetRepository.findByCode(code);
	}

	@Override
	public void checkBudgetState(Budget budgetRequest) {
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
	public Budget estimate(String customerCode, String customerStore, Set<ItemToFindPrice> itemsToEstimate)
			throws CustomerNotFoundException, ItemsNotFoundException {
		return budgetRepository.estimate(customerCode, customerStore, itemsToEstimate);
	}

	@Override
	public void update(Budget budget) {
		budgetRepository.update(budget);

	}

}
