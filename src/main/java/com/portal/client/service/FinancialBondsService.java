package com.portal.client.service;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import com.portal.client.dto.FinancialBondsPageDTO;

public interface FinancialBondsService {

	Optional<FinancialBondsPageDTO> find(int page, int pageSize)
			throws SocketTimeoutException, ConnectException, TimeoutException, SocketException;

	Optional<FinancialBondsPageDTO> findByCustomerCodeStore(int page, int pageSize, String code,String store)
			throws SocketTimeoutException, ConnectException, TimeoutException, SocketException;
}
