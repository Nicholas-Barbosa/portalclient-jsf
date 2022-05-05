package com.farawaybr.portal.service.jsonb;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.enterprise.context.ApplicationScoped;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

@ApplicationScoped
public class JsonbServiceImpl implements JsonbService {

	private final Jsonb jsonbMapper;

	public JsonbServiceImpl() {
		this.jsonbMapper = JsonbBuilder.create();
	}

	@Override
	public String toJson(Object obect) {
		return jsonbMapper.toJson(obect);
	}

	@Override
	public <T> T fromJson(String json, Class<T> type) {
		return jsonbMapper.fromJson(json, type);
	}

	@Override
	public <T> T fromJson(InputStream entityStream, Class<T> type) {
		// TODO Auto-generated method stub
		return jsonbMapper.fromJson(entityStream, type);
	}

	@Override
	public void toJson(Object obect, OutputStream out) {
		jsonbMapper.toJson(obect, out);

	}

}
