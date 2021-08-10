package com.portal.client.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

import com.portal.client.dto.ItemXlsxFileLayout;
import com.portal.client.dto.ItemXlsxProjection;
import com.portal.client.service.ItemImportService;
import com.portal.client.util.jsf.FacesUtils;

@ViewScoped
@Named
public class ItemImportController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6645956604112904363L;

	private UploadedFile fileToRead;

	private String customerCode, customerStore;

	private ItemXlsxFileLayout itemFileLayout;

	private List<ItemXlsxProjection> itemXlsxProjection;

	private ItemImportService itemImporter;

	@Inject
	public ItemImportController(ItemImportService itemImporter) {
		this.itemImporter = itemImporter;
		this.itemFileLayout = new ItemXlsxFileLayout();
		this.itemFileLayout.setOffSetCellForProductCode(0);
		this.itemFileLayout.setOffSetCellForProductQuantity(1);
	}

	public void handleFileUpload(FileUploadEvent event) {
		fileToRead = event.getFile();
		this.itemFileLayout.setXlsxStreams(fileToRead.getContent());
		FacesUtils.info(null, "Sucesso", "Arquivo salvo para leitura", "growl");
	}

	public void readFile() {
		itemXlsxProjection = itemImporter.read(itemFileLayout);
		FacesUtils.info(null, "Planilha lida", null, "growl");
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

	public List<ItemXlsxProjection> getItemXlsxProjection() {
		return itemXlsxProjection;
	}
}
