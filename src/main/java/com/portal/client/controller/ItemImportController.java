package com.portal.client.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.FileUploadEvent;

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
		this.itemFileLayout.setXlsxStreams(event.getFile().getContent());
		FacesUtils.info(null, "Sucesso", "Arquivo salvo para leitura", "growl");
	}

	public void readFile() {
		if (itemFileLayout.getXlsxStreams() != null) {
			itemXlsxProjection = itemImporter.read(itemFileLayout);
			FacesUtils.ajaxUpdate("readResult");
			PrimeFaces.current().executeScript("PF('dlgResult').show()");
			return;
		}
		FacesUtils.error(null, "Arquivo não transferido", "Transfira o arquivo que será lido", "growl");
	}

	public void onCellEdit(CellEditEvent<Object> event) {

		FacesUtils.info(null, event.getColumn().getHeaderText() + " modificado", event.getColumn().getHeaderText()
				+ " da linha " + (event.getRowIndex()+1) + " foi modificado para " + event.getNewValue() + ".");
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
