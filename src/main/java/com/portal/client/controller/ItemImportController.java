package com.portal.client.controller;

import java.io.Serializable;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.FileUploadEvent;

import com.portal.client.dto.BaseBudget;
import com.portal.client.dto.ItemXlsxFileLayout;
import com.portal.client.dto.ItemXlsxProjection;
import com.portal.client.exception.CustomerNotFoundException;
import com.portal.client.exception.ItemsNotFoundException;
import com.portal.client.service.ItemImportService;
import com.portal.client.util.jsf.FacesUtils;
import com.portal.client.vo.WrapperItem404Error.Item404Error;

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

	private Item404Error[] itemsNotFound;

	@Inject
	public ItemImportController(ItemImportService itemImporter) {
		this.itemImporter = itemImporter;
		this.itemFileLayout = new ItemXlsxFileLayout();
		this.itemFileLayout.setInitPosition(1);
		this.itemFileLayout.setOffSetCellForProductCode(1);
		this.itemFileLayout.setOffSetCellForProductQuantity(2);
	}

	public void discoverItems() {
		try {
			BaseBudget findPrice = itemImporter.findPrice(itemXlsxProjection, customerCode, customerStore);
			PrimeFaces.current().dialog().closeDynamic(findPrice);
		} catch (SocketTimeoutException | SocketException | TimeoutException e) {
			// TODO Auto-generated catch block
			FacesUtils.fatal(null, "Problema de rede", "Problema de rede no servidor da Faraway", "growl ");
		} catch (CustomerNotFoundException e) {
			FacesUtils.error(null, "Cliente não encontrado", null, "growl");
		} catch (ItemsNotFoundException e) {
			FacesUtils.error(null, "Itens não econtrados", null, "growl");
			this.itemsNotFound = e.getErrors();
			this.itemXlsxProjection.removeIf(i -> Arrays.stream(itemsNotFound)
					.anyMatch(i404 -> i404.getItemIdentity().equalsIgnoreCase(i.getCode())));
			FacesUtils.executeScript("PF('dlgItemsNotFound').show();PF('dlgResult').hide();");
			FacesUtils.ajaxUpdate("dtItemsNotFound");
		}
	}

	public void handleFileUpload(FileUploadEvent event) {
		this.itemFileLayout.setXlsxStreams(event.getFile().getContent());
		FacesUtils.info(null, "Sucesso", "Arquivo salvo para leitura", "growl");
	}

	public void readFile() {
		this.itemFileLayout.setInitPosition(itemFileLayout.getInitPosition() - 1);
		this.itemFileLayout.setLastPosition(itemFileLayout.getLastPosition() == 0 ? 0
				: itemFileLayout.getLastPosition() == 1 ? 1 : itemFileLayout.getLastPosition() - 1);
		this.itemFileLayout.setOffSetCellForProductCode(itemFileLayout.getOffSetCellForProductCode() - 1);
		this.itemFileLayout.setOffSetCellForProductQuantity(itemFileLayout.getOffSetCellForProductQuantity() - 1);
		if (itemFileLayout.getXlsxStreams() != null) {
			itemXlsxProjection = itemImporter.read(itemFileLayout);
			FacesUtils.ajaxUpdate("readResult");
			PrimeFaces.current().executeScript("PF('dlgResult').show()");
			return;
		}
		FacesUtils.error(null, "Arquivo não transferido", "Transfira o arquivo que será lido", "growl");
	}

	public void cancelExa() {
		this.itemXlsxProjection = null;
	}

	public void onCellEdit(CellEditEvent<Object> event) {
		FacesUtils.info(null, event.getColumn().getHeaderText() + " alterado", event.getColumn().getHeaderText()
				+ " da linha " + (event.getRowIndex() + 1) + " foi alterado para " + event.getNewValue() + ".");

	}

	public void removeItemXlsxProjection(ItemXlsxProjection item) {
		if (this.itemXlsxProjection.remove(item)) {
			if (itemXlsxProjection.size() == 0) {
				PrimeFaces.current().executeScript("PF('dlgResult').hide()");
				this.cancelExa();
			}

		}
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

	public Item404Error[] getItemsNotFound() {
		return itemsNotFound;
	}
}
