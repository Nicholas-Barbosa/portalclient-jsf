package com.portal.client.security.api.register;

public interface ApiRegister {

	ApiRegister token(String token);
	ApiRegister tokenPrefix(String prefix);
	String getUrl();
}
