package com.farawaybr.portal.jsf.controller.components;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

@ViewScoped
@Named
public class TableMismatchFileImportController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4876240355856575204L;
	private String resolvedMessage;
	
	public String getResolvedMessage() {
		return resolvedMessage;
	}
}
