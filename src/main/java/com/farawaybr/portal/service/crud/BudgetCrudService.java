package com.farawaybr.portal.service.crud;

import java.util.Optional;

import com.farawaybr.portal.dto.BudgetFullProjection;
import com.farawaybr.portal.dto.BudgetSemiProjection;
import com.farawaybr.portal.dto.CustomerRepresentativeOrderForm;
import com.farawaybr.portal.service.ServiceSerializable;
import com.farawaybr.portal.vo.Budget;
import com.farawaybr.portal.vo.Page;

public interface BudgetCrudService extends ServiceSerializable, CrudService {

	Optional<Page<BudgetSemiProjection>> findAll(int page, int pageSize,String key);

	void save(Budget budget, CustomerRepresentativeOrderForm ordersForm);

	Optional<BudgetFullProjection> findByCode(String code);

	void checkBudgetState(Budget toCheck);

	void update(Budget budget);
}
