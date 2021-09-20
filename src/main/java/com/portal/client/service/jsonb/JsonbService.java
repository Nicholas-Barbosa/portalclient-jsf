package com.portal.client.service.jsonb;

import java.io.InputStream;

public interface JsonbService {

	String toJson(Object obect);

	<T> T fromJson(String json, Class<T> type);
	
	<T> T fromJson(InputStream entityStream, Class<T> type);
}
