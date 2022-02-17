package com.portal.client.security.auth;

import com.portal.client.dto.LoginProtheusForm;

public interface AuthenticationService {

	void authenticate(LoginProtheusForm login);
	void refreshToken();
}
