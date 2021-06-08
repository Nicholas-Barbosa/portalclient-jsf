package com.portal.java.jaxrs.resource;

import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.portal.java.ejb.EjbFacade;

@Path("hello")
@RequestScoped
public class HelloResource {

	@EJB
	private EjbFacade ejbFacade;

	@EJB
	private EjbFacade ejbFacade2;

	@GET
	public String sayHello() {
		return "hello";
	}

	@PreDestroy
	public void preDestroy() {
		System.out.println("Pre destroy");
	}

	@GET
	@Path("client1")
	public String helloFromFacade() {
		return ejbFacade.sayHello();
	}

	@GET
	@Path("client2")
	public String helloFromFacade2() {
		return ejbFacade2.sayHello();
	}

	@GET
	@Path("{hello}")
	public void setHello(@PathParam("hello") String hello) {
		System.out.println("Hello " + hello);
		ejbFacade.setHello(hello);
	}

}
