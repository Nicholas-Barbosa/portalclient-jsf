package com.portal.repository;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import com.portal.dto.LoginForm;

public interface AuthenticationRepository {

	void login(LoginForm login)
			throws SocketTimeoutException, ConnectException, IllegalArgumentException, TimeoutException;
}
