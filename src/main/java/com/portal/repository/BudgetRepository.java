package com.portal.repository;

import java.io.Serializable;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import com.portal.dto.BudgetEstimateForm;
import com.portal.dto.BudgetEstimatedDTO;

public interface BudgetRepository extends Serializable {

	BudgetEstimatedDTO estimate(BudgetEstimateForm form)throws SocketTimeoutException, ConnectException, TimeoutException ;

}
