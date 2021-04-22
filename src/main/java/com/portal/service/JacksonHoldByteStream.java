package com.portal.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import javax.inject.Singleton;

import com.fasterxml.jackson.databind.ObjectMapper;

@Singleton
public class JacksonHoldByteStream implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4221134634676829296L;
	private final ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * Deserialize the sequential data(stream) and return de converted object.
	 * 
	 * @param <T>
	 * @param inStream sequential data
	 * @param typeObject
	 * @return
	 */
	public <T> T readValue(InputStream inStream, Class<T> typeObject) {
		try {
			return this.objectMapper.readValue(inStream, typeObject);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
