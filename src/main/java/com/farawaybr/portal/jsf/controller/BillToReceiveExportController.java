package com.farawaybr.portal.jsf.controller;

import java.io.Serializable;

import javax.inject.Named;


@javax.faces.view.ViewScoped
@Named
public class BillToReceiveExportController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8798493097984847272L;
	private int records,limitRecords;

	public void export() {
		
	}
	public int getRecords() {
		return records;
	}

	public void setRecords(int records) {
		this.records = records;
	}

	public int getLimitRecords() {
		return limitRecords;
	}

	public void setLimitRecords(int limitRecords) {
		this.limitRecords = limitRecords;
	}
	
	
}
