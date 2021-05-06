package com.portal.repository;

import java.io.Serializable;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import javax.ws.rs.ProcessingException;

import com.portal.dto.CustomerDTO;
import com.portal.dto.CustomerPageDTO;

public interface CustomerRepository extends Serializable {

	CustomerPageDTO getAllByPage(int page, int pageSize) throws SocketTimeoutException, ConnectException,
			ProcessingException,  TimeoutException, SocketException;

	Optional<CustomerDTO> getByCodeAndStore(String code, String store) throws SocketTimeoutException, ConnectException,
			ProcessingException,TimeoutException, SocketException;

}
