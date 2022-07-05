package com.farawaybr.portal.jsf.controller;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.Response;

import com.farawaybr.portal.cdi.aop.annotations.NotAuthorizedJoinPointCut;

@Named
@ApplicationScoped
public class pushBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotAuthorizedJoinPointCut
	public void clockAction() {
		throw new NotAuthorizedException(Response.status(401));
	}
}
