package com.portal.java.service;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import com.portal.java.dto.FinancialBondsPageDTO;

public interface FinancialBondsService {

	Optional<FinancialBondsPageDTO> find(int page, int pageSize)
			throws SocketTimeoutException, ConnectException, TimeoutException, SocketException;

	Optional<FinancialBondsPageDTO> findByCustomerName(int page, int pageSize, String name)
			throws SocketTimeoutException, ConnectException, TimeoutException, SocketException;
}
