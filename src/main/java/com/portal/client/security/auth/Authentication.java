package com.portal.client.security.auth;

import com.portal.client.dto.LoginProtheusForm;

public interface Authentication {

	void authenticate(LoginProtheusForm login);
}
