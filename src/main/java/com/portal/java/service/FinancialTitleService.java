package com.portal.java.service;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import com.portal.java.dto.FinancialTitlePageDTO;

public interface FinancialTitleService {

	FinancialTitlePageDTO find(int page, int pageSize)throws SocketTimeoutException, ConnectException, TimeoutException,SocketException;
	
}
