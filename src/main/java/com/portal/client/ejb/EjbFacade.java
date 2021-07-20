package com.portal.client.ejb;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.PostActivate;
import javax.ejb.PrePassivate;
import javax.ejb.Remove;
import javax.ejb.Stateful;

@Stateful
//@StatefulTimeout(unit = TimeUnit.SECONDS, value = 0)
public class EjbFacade {

	/**
	 * 
	 */
	// private static final long serialVersionUID = 5219439203892710914L;

	private String hello;

	private final Logger log = Logger.getLogger("EjbFacadeLogger");

	@Remove
	public void remove() {
		log.info("Remove...");
	}

	@PreDestroy
	public void preDestroy() {
		log.info("Destroy...");
	}

	@PostActivate
	public void postActivate() {
		log.info("Post activate");
	}

	@PostConstruct
	public void postConstruct() {
		log.info("Post construct");
	}

	@PrePassivate
	public void prePassivate() {
		log.info("pre passivate");
	}

	public String sayHello() {
		return hello == null ? "ola" : hello;
	}

	public void setHello(String hello) {
		this.hello = hello;
	}
}
