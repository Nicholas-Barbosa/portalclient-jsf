package com.portal.repository;

import java.io.Serializable;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import javax.ws.rs.ProcessingException;

import com.portal.dto.QuoteBudgetForm;
import com.portal.dto.ResponseQuoteBudgetDTO;

public interface BudgetRepository extends Serializable {

	ResponseQuoteBudgetDTO quote(QuoteBudgetForm form) throws SocketTimeoutException, ConnectException,
			ProcessingException, IllegalArgumentException, SocketException, TimeoutException;
}
