package com.portal.client.service.crud;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.portal.client.cdi.aop.annotations.OrderRepresentativeSetterJoinPointCut;
import com.portal.client.dto.BudgetFullProjection;
import com.portal.client.dto.CustomerRepresentativeOrderForm;
import com.portal.client.dto.ProspectCustomerOnOrder;
import com.portal.client.exception.CustomerNotAllowed;
import com.portal.client.repository.BudgetRepository;
import com.portal.client.service.OrderBehaviorHelper;
import com.portal.client.vo.Budget;
import com.portal.client.vo.Page;

@ApplicationScoped
public class BudgetCrudServiceImpl implements BudgetCrudService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4268548772630741803L;

	private BudgetRepository budgetRepository;

	private OrderBehaviorHelper orderHelper;

	private OrderRprensentativeSetter orderRepSetter;

	@Inject
	public BudgetCrudServiceImpl(BudgetRepository budgetRepository, OrderBehaviorHelper orderHelper,
			OrderRprensentativeSetter authorSetter) {
		super();
		this.budgetRepository = budgetRepository;
		this.orderHelper = orderHelper;
		this.orderRepSetter = authorSetter;
	}

	@Override
	public Page<Budget> findAll(int page, int pageSize) {
		return budgetRepository.findAll(page, pageSize);

	}

	@OrderRepresentativeSetterJoinPointCut
	@Override
	public void save(Budget budget, CustomerRepresentativeOrderForm ordersForm) {
		checkBudgetState(budget);
		budget.setCustomerNumOrder(ordersForm.getCustomerOrder());
		budget.setRepNumOrder(ordersForm.getRepresentativeOrder());
		budgetRepository.save(budget);
	}

	@Override
	public Optional<BudgetFullProjection> findByCode(String code) {
		Optional<BudgetFullProjection> maybe = budgetRepository.findByCode(code);
		maybe.ifPresent(budget -> {
			orderHelper.sumStValue(budget);
			orderRepSetter.setAutor(budget);
//			budget.getItems().stream().map(Item::getPriceData).forEach(pData -> {
//				ItemDiscounData data = ItemDiscountDataBuilder.getInstance().with
//			});
		});
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
