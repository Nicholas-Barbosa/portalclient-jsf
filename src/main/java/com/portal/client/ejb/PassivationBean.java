package com.portal.client.ejb;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.PrePassivate;
import javax.ejb.Remove;
import javax.ejb.Stateful;

@Stateful(passivationCapable = true)
public class PassivationBean {

	@PostConstruct
	public void construct() {
		System.out.println("Post construct");
	}
	@PrePassivate
	public void prePassivate(){
		System.out.println("Pre passivate");
	}
	
	@PreDestroy
	public void preDestroy() {
		System.out.println("Pre destroy");
	}
	@Remove
	public void remove() {
		System.out.println("remove");
	}
}
