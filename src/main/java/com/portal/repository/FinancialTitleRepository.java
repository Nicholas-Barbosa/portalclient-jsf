package com.portal.repository;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import com.portal.dto.FinancialTitlePageDTO;

public interface FinancialTitleRepository {

	FinancialTitlePageDTO find(int page, int pageSize)throws SocketTimeoutException, ConnectException, TimeoutException;
}
