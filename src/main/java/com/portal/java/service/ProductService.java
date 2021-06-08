package com.portal.java.service;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import com.portal.java.dto.BaseProductDTO;
import com.portal.java.dto.ProductDTO;
import com.portal.java.dto.ProductPageDTO;

public interface ProductService extends ServiceSerializable {

	Optional<ProductPageDTO> findByDescription(String descriptio, int page, int pageSize)throws SocketTimeoutException, ConnectException, TimeoutException;

	Optional<ProductDTO> findByCode(String code)throws SocketTimeoutException, ConnectException, TimeoutException;

	void loadImage(Collection<? extends BaseProductDTO> products);

	void loadImage(ProductDTO productDTO);
}
