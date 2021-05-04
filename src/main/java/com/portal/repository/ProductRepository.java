package com.portal.repository;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import javax.ws.rs.ProcessingException;

import com.portal.pojo.ProductPage;
import com.portal.pojo.Product;

public interface ProductRepository {

	Optional<Product> getByCode(String code) throws SocketTimeoutException, ConnectException, ProcessingException,
			IllegalArgumentException, TimeoutException;

	ProductPage getAllByPage(int page, int pageSize) throws SocketTimeoutException, ConnectException,
			ProcessingException, IllegalArgumentException, TimeoutException;

	ProductPage getByDescription(int page, int pageSize, String description) throws SocketTimeoutException,
			ConnectException, ProcessingException, IllegalArgumentException, TimeoutException;
}
