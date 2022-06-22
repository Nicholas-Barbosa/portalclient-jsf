package com.farawaybr.portal.jsf.controller;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.Push;
import org.omnifaces.cdi.PushContext;


@Named
@ApplicationScoped
public class pushBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	@Push(channel = "clock")
	private PushContext push;

	public void clockAction() {
		System.out.println("send message!!");
		System.out.println(push.send("Mensagem!!"));
	}
}
