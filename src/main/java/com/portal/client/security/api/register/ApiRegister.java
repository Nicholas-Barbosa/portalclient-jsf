package com.portal.client.security.api.register;

import com.portal.client.security.api.ApiData;
import com.portal.client.security.user.User;

public interface ApiRegister<R extends ApiRegister<R>> {

	R token(String token);

	R tokenPrefix(String prefix);

	ApiData register();

	R setUser(User user);
}
