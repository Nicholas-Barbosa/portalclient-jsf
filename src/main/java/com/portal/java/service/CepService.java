package com.portal.java.service;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import com.portal.java.pojo.ZipCode;

public interface CepService {

	Optional<ZipCode> find(String cep)throws SocketTimeoutException, ConnectException, SocketException, TimeoutException;
}
