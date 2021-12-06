package com.portal.client.security.api.register;

import com.portal.client.dto.RepresentativeData;
import com.portal.client.security.api.ApiData;

public interface ApiRegister<R extends ApiRegister<R>> {

	R token(String token);

	R tokenPrefix(String prefix);

	ApiData register();

	R setUser(RepresentativeData user);
}
