package com.portal.java.repository;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

import com.portal.java.dto.NoPageProductResponseDTO;
import com.portal.java.dto.ProductPageDTO;

public interface ProductRepository {

	NoPageProductResponseDTO findByCode(String code)throws SocketTimeoutException, ConnectException, TimeoutException;

	Future<NoPageProductResponseDTO> findByCodeAsync(String code)throws SocketTimeoutException, ConnectException, TimeoutException;

	ProductPageDTO find(int page, int pageSize)throws SocketTimeoutException, ConnectException, TimeoutException;

	Optional<ProductPageDTO> findByDescription(int page, int pageSize, String description)throws SocketTimeoutException, ConnectException, TimeoutException;
}