package com.portal.client.service.crud;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import com.portal.client.dto.Product;
import com.portal.client.dto.ProductPageDTO;
import com.portal.client.service.ServiceSerializable;

public interface ProductService extends ServiceSerializable {

	Optional<ProductPageDTO> findByDescription(String descriptio, int page, int pageSize)
			throws SocketTimeoutException, ConnectException, TimeoutException, SocketException;

	Optional<Product> findByCode(String code, String customerCode, String store)
			throws SocketTimeoutException, ConnectException, TimeoutException, SocketException;

	Optional<Product> findByCodeForProspect(String code, String state, String sellerType)
			throws SocketTimeoutException, ConnectException, TimeoutException, SocketException;

	void loadImage(Product productDTO);

}
