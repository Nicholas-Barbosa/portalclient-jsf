package com.farawaybr.portal.security.auth;

import com.farawaybr.portal.dto.LoginProtheusForm;

public interface AuthenticationService {

	void authenticate(LoginProtheusForm login);

	void refreshToken();
}
