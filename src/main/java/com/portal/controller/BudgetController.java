package com.portal.controller;

import java.io.Serializable;

import javax.faces.component.UIData;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.component.blockui.BlockUI;

import com.portal.dto.GssResponseClientsDTO;

@Named
@ViewScoped
public class BudgetController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private GssResponseClientsDTO gssResponseClients;

	private UIData uiCustomerDataTable;

	private BlockUI uiBlockForm;

	private boolean render = true;

	private String divLoadingMainMessage;

	public void getClientsFromWS() {
		try {
			Thread.sleep(2500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * called by <f:event listener="#{budgetController.preRenderUIBlockCustomerForm}
	 * event="preRenderComponenet"/>
	 */
	public void preRenderUIBlockCustomerForm() {
		this.uiBlockForm.setBlock("formClientTb");
		this.uiBlockForm.setBlocked(true);

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

	public BlockUI getUiBlockForm() {
		return uiBlockForm;
	}

	public void setUiBlockForm(BlockUI uiBlockForm) {
		this.uiBlockForm = uiBlockForm;

	}

	public String getDivLoadingMainMessage() {
		return divLoadingMainMessage;
	}

	public void setDivLoadingMainMessage(String divLoadingMainMessage) {
		this.divLoadingMainMessage = divLoadingMainMessage;
	}

}
