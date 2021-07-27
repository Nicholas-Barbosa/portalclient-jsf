package com.portal.client.client.rest.providers.message.reader;

import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

@Provider
@Consumes(MediaType.APPLICATION_JSON)
public class JsonMessageReader implements MessageBodyReader<Object> {

	private final Jsonb jsonReader;

	public JsonMessageReader() {
		super();
		this.jsonReader = JsonbBuilder.create();
	}

	@Override
	public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return true;
	}

	@Override
	public Object readFrom(Class<Object> type, Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, String> httpHeaders, InputStream entityStream) {
		try {
			
			return jsonReader.fromJson(entityStream, type);
		} catch (Exception e) {
			throw new ProcessingException("Error while deserializing Object or covariant: " + type.getName(), e);
		}

	}

}
