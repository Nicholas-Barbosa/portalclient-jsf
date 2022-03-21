package com.portal.client.service.crud;

import java.util.Optional;

import com.portal.client.dto.BudgetFullProjection;
import com.portal.client.dto.BudgetSemiProjection;
import com.portal.client.dto.CustomerRepresentativeOrderForm;
import com.portal.client.service.ServiceSerializable;
import com.portal.client.vo.Budget;
import com.portal.client.vo.Page;

public interface BudgetCrudService extends ServiceSerializable, CrudService {

	Optional<Page<BudgetSemiProjection>> findAll(int page, int pageSize,String key);

	void save(Budget budget, CustomerRepresentativeOrderForm ordersForm);

	Optional<BudgetFullProjection> findByCode(String code);

	void checkBudgetState(Budget toCheck);

	void update(Budget budget);
}
