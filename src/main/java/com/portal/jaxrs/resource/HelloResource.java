package com.portal.jaxrs.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.HttpHeaders;

@Path("hello")
public class HelloResource {
	private Integer page = 1;

	@GET
	public String sayHello() {
		Client client = ClientBuilder.newClient();
		while (page <= 500) {
			WebTarget web = client.target("http://192.168.0.201:8090/rest/products").queryParam("page", page++);
			Builder builder = web.request().header(HttpHeaders.AUTHORIZATION, "Bearer "
					+ "eyJhbGciOiJSUzUxMiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJUT1RWUy1BRFZQTC1GV0pXVCIsInN1YiI6Im1hbm8ucmVwIiwiaWF0IjoxNjIwMjUyNzQ5LCJ1c2VyaWQiOiIwMDAyNTMiLCJleHAiOjE2MjAyNTYzNDksImVudklkIjoiREJfQ0RHIn0.fjkveiqZ4GNWCrU_TfJ74mDnHmmx1l1_6rcFo2ULKyM9bu0EHHvVXqCHaMVe19pzK8eqzbOE51Qqskr9QIuyw6I2qudjMbOWhr33nofje-p3JYya5ZOzK7rJ1hXKSftw-yAmuv4FZLhN7Z4CvvGyjMXj6v0epmLxynXbRJD5vOsvcvBoSkVbh-hmtfIfhFO5ByCdw5huicQwD3q9bGLHnijJdeIj_8kJWBJZ26ABozA8eDbkFeLeYcH5Z2IZHDaKNBG0qtaL5QnhF_A06yJjmSyQzBIZr31GFp7KTTkSvwgH7kmjKS_M0njLApKyhSafMJ6cJ_EeU0ZIAmKYoUKNXw");
			System.out.println(builder.get(String.class));
		}
		return "hello";
	}
}
