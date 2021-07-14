package com.portal.java.repository;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import com.portal.java.dto.FinancialBondsPageDTO;

public interface FinancialBondsRepository {

	FinancialBondsPageDTO find(int page, int pageSize)
			throws SocketTimeoutException, ConnectException, TimeoutException, SocketException;

	FinancialBondsPageDTO findByCustomerName(int page, int pageSize, String name)
			throws SocketTimeoutException, ConnectException, TimeoutException, SocketException;
}
