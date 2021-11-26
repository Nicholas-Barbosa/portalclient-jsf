package com.portal.client.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;

import com.portal.client.exceptionhandler.netowork.NetworkExceptionObserver;

@SessionScoped
public class OnNetworkExceptionController implements NetworkExceptionObserver, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5355380388116956853L;

	@Override
	public void onException(Exception e) {
		// TODO Auto-generated method stub

	}

}
