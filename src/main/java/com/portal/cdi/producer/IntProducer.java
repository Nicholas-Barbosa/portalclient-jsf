package com.portal.cdi.producer;

import javax.ejb.Singleton;
import javax.enterprise.inject.Produces;

@Singleton
public class IntProducer {

	private Integer num = 0;

	@Produces
	public int getInstance() {
		return ++num;
	}
}
