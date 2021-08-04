package com.portal.client.service.jsonb;

public interface JsonbService {

	String toJson(Object obect);

	<T> T fromJson(String json, Class<T> type);
}
