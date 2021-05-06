package com.portal.repository;

import java.io.Serializable;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import javax.ws.rs.ProcessingException;

import com.portal.dto.BudgetEstimateDTO;
import com.portal.dto.BudgetEstimateForm;

public interface BudgetRepository extends Serializable {

	BudgetEstimateDTO estimate(BudgetEstimateForm form) throws SocketTimeoutException, ConnectException,
			ProcessingException, IllegalArgumentException, SocketException, TimeoutException;
}
