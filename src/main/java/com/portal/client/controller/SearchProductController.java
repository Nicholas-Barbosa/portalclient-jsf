package com.portal.client.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

@ViewScoped
@Named
public class SearchProductController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3733963320781310655L;

	private String customerCode, customerStore;

}
