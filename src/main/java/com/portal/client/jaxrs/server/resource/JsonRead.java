package com.portal.client.jaxrs.server.resource;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.JsonbException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.portal.client.dto.WrapperRepresentativeData;
import com.portal.client.service.jsonb.JsonbService;

@Path("json")
@ApplicationScoped
public class JsonRead {

	@Inject
	private JsonbService service;

	private final String jsonToRead = "{\r\n" + "    \"representative\": [\r\n" + "        {\r\n"
			+ "            \"code\": \"000030\",\r\n" + "            \"invoice\": 424136.31,\r\n"
			+ "            \"mark\": 492124.25,\r\n" + "            \"open_orders\": 18950.07,\r\n"
			+ "            \"name\": \"MANO REPRESENTACOES LTDA\",\r\n" + "            \"fantasy_name\": \"MANO\",\r\n"
			+ "            \"email\": \"vendas@manorepresentacoes.com.br\"\r\n" + "        }\r\n" + "    ]\r\n" + "}";

	private byte[] jsonbException;

	public void setJsonbException(byte[] jsonbException) {
		this.jsonbException = jsonbException;
	}

	@GET
	public Response read() {
		try {
			System.out.println(service.fromJson(jsonToRead, WrapperRepresentativeData.class));
			return Response.ok("ok,lido!").build();
		} catch (JsonbException e) {
			try {
				new ObjectMapper().readValue(jsonToRead, WrapperRepresentativeData.class);
				return Response.status(500, "jsonb exception: mas lido pelo jackson!").build();
			} catch (JsonProcessingException e1) {
			}
			return Response.status(500, "jsonb exception: " + e.getMessage()).build();
		}
	}

	@GET
	@Path("exception")
	@Produces(MediaType.APPLICATION_JSON)
	public Response jsonbException() {

		return jsonbException == null || jsonbException.length == 0 ? Response.noContent().build()
				: Response.ok(new String(jsonbException)).build();
	}
}
