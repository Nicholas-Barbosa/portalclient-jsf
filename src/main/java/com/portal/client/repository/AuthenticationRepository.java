package com.portal.client.repository;

import com.portal.client.dto.LoginProtheusForm;

public interface AuthenticationRepository {

	void login(LoginProtheusForm login);
}
