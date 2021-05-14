package com.portal.client.rest.providers.message.reader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

@Provider
@Consumes("aplication/json")
@Produces("aplication/json")
public class JsonObjectMessageReaderWriter implements MessageBodyReader<Object>, MessageBodyWriter<Object> {

	private final Jsonb jsonReader;

	public JsonObjectMessageReaderWriter() {
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

	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void writeTo(Object t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
			throws IOException, WebApplicationException {
		try {
			System.out.println("Write!");
			jsonReader.toJson(entityStream);
		} catch (Exception e) {
			throw new ProcessingException("Error while serializing Object or covariant: " + type.getName(), e);
		}

	}

}
