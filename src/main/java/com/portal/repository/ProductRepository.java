package com.portal.repository;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

import com.portal.dto.NoPageProductResponseDTO;
import com.portal.dto.ProductPageDTO;

public interface ProductRepository {

	NoPageProductResponseDTO findByCode(String code)throws SocketTimeoutException, ConnectException, TimeoutException;

	Future<NoPageProductResponseDTO> findByCodeAsync(String code)throws SocketTimeoutException, ConnectException, TimeoutException;

	ProductPageDTO find(int page, int pageSize)throws SocketTimeoutException, ConnectException, TimeoutException;

	Optional<ProductPageDTO> findByDescription(int page, int pageSize, String description)throws SocketTimeoutException, ConnectException, TimeoutException;
}
