package com.portal.java.repository;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

import com.portal.java.dto.Product;
import com.portal.java.dto.ProductJsonWrapper;
import com.portal.java.dto.ProductPageDTO;

public interface ProductRepository {

	ProductPageDTO find(int page, int pageSize)
			throws SocketTimeoutException, ConnectException, TimeoutException, SocketException;

	Optional<ProductPageDTO> findByDescription(int page, int pageSize, String description)
			throws SocketTimeoutException, ConnectException, TimeoutException, SocketException;

	Optional<Product> findByCode(String code, String customerCode, String store)
			throws SocketTimeoutException, ConnectException, TimeoutException, SocketException;

	Future<ProductJsonWrapper> findByCodeAsync(String code, String customerCode, String store)
			throws SocketTimeoutException, ConnectException, TimeoutException, SocketException;

	Future<ProductJsonWrapper> findByCodeForProspectAsync(String code, String state, String sellerType)
			throws SocketTimeoutException, ConnectException, TimeoutException, SocketException;
}
