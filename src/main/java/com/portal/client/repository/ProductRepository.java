package com.portal.client.repository;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

import com.portal.client.vo.Product;
import com.portal.client.vo.ProductPage;
import com.portal.client.vo.ProductPageDTO;

public interface ProductRepository {

	ProductPageDTO find(int page, int pageSize)
			throws SocketTimeoutException, ConnectException, TimeoutException, SocketException;

	Optional<ProductPageDTO> findByDescription(int page, int pageSize, String description)
			throws SocketTimeoutException, ConnectException, TimeoutException, SocketException;

	Optional<Product> findByCode(String code, String customerCode, String store)
			throws SocketTimeoutException, ConnectException, TimeoutException, SocketException;

	Future<ProductPage> findByCodeAsync(String code, String customerCode, String store)
			throws SocketTimeoutException, ConnectException, TimeoutException, SocketException;

	Future<ProductPage> findByCodeForProspectAsync(String code, String state, String sellerType)
			throws SocketTimeoutException, ConnectException, TimeoutException, SocketException;
}
