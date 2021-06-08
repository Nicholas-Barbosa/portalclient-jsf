package com.portal.java.client.rest.providers.message.writer;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.config.PropertyVisibilityStrategy;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class JsonMessageWriter implements MessageBodyWriter<Object> {

	private final Jsonb jsonWriter;

	public JsonMessageWriter() {
		super();
		this.jsonWriter = JsonbBuilder.create(jsonbConfig());
	}

	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return true;
	}

	@Override
	public void writeTo(Object t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
			throws IOException, WebApplicationException {
		// TODO Auto-generated method stub
		try {
			jsonWriter.toJson(t, entityStream);
		} catch (Exception e) {
			throw new ProcessingException("Error while deserializing Object or covariant: " + type.getName(), e);
		}
	}

	private JsonbConfig jsonbConfig() {
		PropertyVisibilityStrategy vis = new PropertyVisibilityStrategy() {
			@Override
			public boolean isVisible(Field field) {
				return field.isAnnotationPresent(JsonbProperty.class);
			}

			@Override
			public boolean isVisible(Method method) {
				return method.isAnnotationPresent(JsonbProperty.class);
			}
		};
		return new JsonbConfig().withPropertyVisibilityStrategy(vis);
	}
}
