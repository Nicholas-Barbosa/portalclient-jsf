package com.portal.repository;

import java.io.Serializable;

import javax.ws.rs.ProcessingException;

import com.portal.dto.BudgetEstimateForm;
import com.portal.dto.BudgetEstimatedDTO;

public interface BudgetRepository extends Serializable {

	BudgetEstimatedDTO estimate(BudgetEstimateForm form) throws ProcessingException;

}
