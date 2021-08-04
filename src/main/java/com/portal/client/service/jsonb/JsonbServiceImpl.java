package com.portal.client.service.jsonb;

import javax.enterprise.context.ApplicationScoped;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

@ApplicationScoped
public class JsonbServiceImpl implements JsonbService {

	private final Jsonb jsonbMapper;

	public JsonbServiceImpl() {
		super();
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

}
