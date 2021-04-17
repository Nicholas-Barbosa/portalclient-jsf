package com.portal.controller;

import java.io.Serializable;

import javax.faces.component.UIData;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.portal.dto.GssResponseClientsDTO;
import com.portal.service.restclient.RestClientHeader;
import com.portal.service.restclient.RestClientTemplate;

@Named
@ViewScoped
public class BudgetController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private RestClientTemplate restClient;

	private GssResponseClientsDTO gssResponseClients;

	private UIData uiCustomerDataTable;

	private boolean render = true;

	public BudgetController() {
		// TODO Auto-generated constructor stub
	}

	@Inject
	public BudgetController(RestClientTemplate restClient) {
		super();
		this.restClient = restClient;
	}

	public void getClientsFromWS() {

		gssResponseClients = restClient.getForEntity("http://192.168.0.246:8091/rest/clients",
				GssResponseClientsDTO.class, new RestClientHeader("Authorization",
						"Bearer eyJhbGciOiJSUzUxMiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJUT1RWUy1BRFZQTC1GV0pXVCIsInN1YiI6Im1hbm8ucmVwIiwiaWF0IjoxNjE4Njc4OTY1LCJ1c2VyaWQiOiIwMDAyNTMiLCJleHAiOjE2MTg2ODI1NjUsImVudklkIjoiREJfQ0RHIn0.ouXDU8Bq5pAsSk5KE64pTj6zxzi62-jZnNRYkOjJac5-r-xH6GETE7Z0uLoEPc4N65ZoAwAqWBx8TB2IoqVxz_SnGie6nKLM-lg_TYiusiqi4UgCJitTgjEhw22sU_Evoo48O-E12v-3E5dUXC8_yxW2bf7azoZserdM1dqarK1AxpSs92yXbqltVXvwRWxi5BjYwI1PaY82Q6-Zf5xblYFJMIY-sTVJkenEykgbSnYQpVZzhFtrg4xbX4xfDXfCK1SheXBq5FsUJ-EFPUTDG1v-3UpHBBxQsos0c5L5FdhRTrF6LlBEFa7E_pghsw7jVMnpIwLTjg0MmZrH_7hr6w"));

	}

	public boolean getRender() {
		return this.render;
	}

	public GssResponseClientsDTO getGssResponseClients() {
		return gssResponseClients;
	}

	public UIData getUiCustomerDataTable() {
		return uiCustomerDataTable;
	}

	public void setUiCustomerDataTable(UIData uiCustomerDataTable) {
		this.uiCustomerDataTable = uiCustomerDataTable;
	}
}
