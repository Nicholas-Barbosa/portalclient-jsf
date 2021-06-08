package com.portal.java.repository;

import java.io.Serializable;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import com.portal.java.dto.BudgetEstimateForm;
import com.portal.java.dto.BudgetEstimatedDTO;

public interface BudgetRepository extends Serializable {

	BudgetEstimatedDTO estimate(BudgetEstimateForm form)throws SocketTimeoutException, ConnectException, TimeoutException ;

}
