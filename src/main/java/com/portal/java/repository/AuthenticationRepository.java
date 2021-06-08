package com.portal.java.repository;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import com.portal.java.dto.LoginForm;

public interface AuthenticationRepository {

	void login(LoginForm login)throws SocketTimeoutException, ConnectException, TimeoutException;
}
