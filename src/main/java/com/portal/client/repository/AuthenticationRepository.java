package com.portal.client.repository;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import com.portal.client.dto.LoginForm;

public interface AuthenticationRepository {

	void login(LoginForm login)throws SocketTimeoutException, ConnectException, TimeoutException,SocketException;
}
