package com.portal.client.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

import com.portal.client.dto.ItemXlsxFileLayout;
import com.portal.client.util.jsf.FacesUtils;

@ViewScoped
@Named
public class ImportItemsController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6645956604112904363L;

	private UploadedFile fileToRead;

	private String customerCode, customerStore;

	private ItemXlsxFileLayout itemFileLayout;

	public ImportItemsController() {
		this.itemFileLayout = new ItemXlsxFileLayout();
	}

	public void handleFileUpload(FileUploadEvent event) {
		fileToRead = event.getFile();
		FacesUtils.info(null, "Sucesso", "Arquivo salvo para leitura", "growl");
	}


	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerStore() {
		return customerStore;
	}

	public void setCustomerStore(String customerStore) {
		this.customerStore = customerStore;
	}

	public ItemXlsxFileLayout getItemFileLayout() {
		return itemFileLayout;
	}

}
