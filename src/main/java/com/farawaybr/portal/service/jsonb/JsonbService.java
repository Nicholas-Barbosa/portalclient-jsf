package com.farawaybr.portal.service.jsonb;

import java.io.InputStream;
import java.io.OutputStream;

public interface JsonbService {

	String toJson(Object obect);

	void toJson(Object obect, OutputStream out);

	<T> T fromJson(String json, Class<T> type);

	<T> T fromJson(InputStream entityStream, Class<T> type);
}
