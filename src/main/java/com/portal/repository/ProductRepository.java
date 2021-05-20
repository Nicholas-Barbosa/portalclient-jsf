package com.portal.repository;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import javax.ws.rs.ProcessingException;

import com.portal.dto.ProductQueryDTO;
import com.portal.dto.ProductQueryPageDTO;

public interface ProductRepository {

	Optional<ProductQueryDTO> getByCode(String code) throws SocketTimeoutException, ConnectException, ProcessingException, TimeoutException,SocketException;

	ProductQueryPageDTO getAllByPage(int page, int pageSize) throws SocketTimeoutException, ConnectException,
			ProcessingException, TimeoutException,SocketException;

	Optional<ProductQueryPageDTO> getByDescription(int page, int pageSize, String description) throws SocketTimeoutException,
			ConnectException, ProcessingException, TimeoutException,SocketException;
}
