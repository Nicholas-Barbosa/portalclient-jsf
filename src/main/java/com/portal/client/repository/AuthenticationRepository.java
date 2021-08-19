package com.portal.client.repository;

import com.portal.client.dto.LoginForm;

public interface AuthenticationRepository {

	void login(LoginForm login);
}
