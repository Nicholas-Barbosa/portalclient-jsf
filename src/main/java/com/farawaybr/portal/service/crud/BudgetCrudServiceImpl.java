package com.farawaybr.portal.service.crud;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.farawaybr.portal.cdi.aop.annotations.OrderRepresentativeSetterJoinPointCut;
import com.farawaybr.portal.dto.BudgetFullProjection;
import com.farawaybr.portal.dto.BudgetSemiProjection;
import com.farawaybr.portal.dto.CustomerRepresentativeOrderForm;
import com.farawaybr.portal.exception.CustomerNotAllowed;
import com.farawaybr.portal.repository.BudgetRepository;
import com.farawaybr.portal.vo.Budget;
import com.farawaybr.portal.vo.Page;
import com.farawaybr.portal.vo.ProspectCustomerOnOrder;

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
	public Optional<Page<BudgetSemiProjection>> findAll(int page, int pageSize, String key) {
		return budgetRepository.findAll(page, pageSize, key);

	}

	@OrderRepresentativeSetterJoinPointCut
	@Override
	public void save(Budget budget, CustomerRepresentativeOrderForm ordersForm) {
		checkBudgetState(budget);
		budget.setRepNumOrder(ordersForm.getRepresentativeOrder());
		budget.setCustomerNumOrder(ordersForm.getCustomerOrder());

		budgetRepository.save(budget);
	}

	@Override
	public Optional<BudgetFullProjection> findByCode(String code) {
		Optional<BudgetFullProjection> maybe = budgetRepository.findByCode(code);

		return maybe;
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
	public void update(Budget budget) {
		budgetRepository.update(budget);

	}

}
