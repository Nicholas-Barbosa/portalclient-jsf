package com.portal.repository;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import javax.ws.rs.ProcessingException;

import com.portal.dto.ProductDTO;
import com.portal.dto.ProductPageDTO;

public interface ProductRepository {

	Optional<ProductDTO> getByCode(String code) throws SocketTimeoutException, ConnectException, ProcessingException, TimeoutException,SocketException;

	ProductPageDTO getAllByPage(int page, int pageSize) throws SocketTimeoutException, ConnectException,
			ProcessingException, TimeoutException,SocketException;

	Optional<ProductPageDTO> getByDescription(int page, int pageSize, String description) throws SocketTimeoutException,
			ConnectException, ProcessingException, TimeoutException,SocketException;
}
