package com.portal.client.repository;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import com.portal.client.dto.FinancialBondsPage;

public interface FinancialBondsRepository {

	FinancialBondsPage find(int page, int pageSize)
			throws SocketTimeoutException, ConnectException, TimeoutException, SocketException;

	FinancialBondsPage findByCustomerCodeStore(int page, int pageSize, String code, String store)
			throws SocketTimeoutException, ConnectException, TimeoutException, SocketException;
}
