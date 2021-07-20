package com.portal.client.repository;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import com.portal.client.dto.FinancialBondsPageDTO;

public interface FinancialBondsRepository {

	FinancialBondsPageDTO find(int page, int pageSize)
			throws SocketTimeoutException, ConnectException, TimeoutException, SocketException;

	FinancialBondsPageDTO findByCustomerCodeStore(int page, int pageSize, String code, String store)
			throws SocketTimeoutException, ConnectException, TimeoutException, SocketException;
}
