package com.portal.client.service;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import com.portal.client.pojo.ZipCode;

public interface ZipCodeService {

	Optional<ZipCode> find(String cep)throws SocketTimeoutException, ConnectException, SocketException, TimeoutException;
}
