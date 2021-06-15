package com.portal.java.service;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import com.portal.java.dto.BaseProductDTO;
import com.portal.java.dto.ProductDTO;
import com.portal.java.dto.ProductPageDTO;

public interface ProductService extends ServiceSerializable {

	Optional<ProductPageDTO> findByDescription(String descriptio, int page, int pageSize)
			throws SocketTimeoutException, ConnectException, TimeoutException, SocketException;

	Optional<ProductDTO> findByCode(String code, String customerCode, String store)
			throws SocketTimeoutException, ConnectException, TimeoutException, SocketException;

	void changeProductQuantity(ProductDTO product);

	void changeProductDiscount(ProductDTO product);
	
	void loadImage(Collection<? extends BaseProductDTO> products);

	void loadImage(ProductDTO productDTO);
	
	
}
