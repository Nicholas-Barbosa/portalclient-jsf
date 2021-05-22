package com.portal.repository;

import com.portal.dto.LoginForm;

public interface AuthenticationRepository {

	void login(LoginForm login);
}
