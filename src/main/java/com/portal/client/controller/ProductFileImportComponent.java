package com.portal.client.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;

import com.portal.client.dto.ProductXlsxFileReadLayout;
import com.portal.client.dto.ProductXlsxFileReadProjection;
import com.portal.client.service.ProductImporter;
import com.portal.client.util.jsf.FacesUtils;

@ViewScoped
@Named
public class ProductFileImportComponent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6376956116588422044L;

	@Inject
	private ProductImporter importer;

	private ProductXlsxFileReadLayout fileLayout;

	private List<ProductXlsxFileReadProjection> productsProjection;

	public ProductFileImportComponent() {
		fileLayout = new ProductXlsxFileReadLayout();
		fileLayout.setInitPosition(1);
		fileLayout.setOffSetCellForProductCode(1);
		fileLayout.setOffSetCellForProductQuantity(2);
	}

	public String onFlowProcess(FlowEvent event) {
		switch (event.getNewStep()) {
		case "setFileLayout":
			if (this.fileLayout.getXlsxStreams() == null || this.fileLayout.getXlsxStreams().length == 0) {
				FacesUtils.error(null, "Arquivo não detectado", "Realize o upload!", "growl");
				return event.getOldStep();
			}
			return event.getNewStep();
		case "readProjection":
			this.fileLayout.setInitPosition(fileLayout.getInitPosition() - 1);
			this.fileLayout.setLastPosition(fileLayout.getLastPosition() == 0 ? 0
					: fileLayout.getLastPosition() == 1 ? 1 : fileLayout.getLastPosition() - 1);
			this.fileLayout.setOffSetCellForProductCode(fileLayout.getOffSetCellForProductCode() - 1);
			this.fileLayout.setOffSetCellForProductQuantity(fileLayout.getOffSetCellForProductQuantity() - 1);
			this.productsProjection = importer.read(fileLayout);
			System.out.println(productsProjection);
			FacesUtils.ajaxUpdate("dtProjection");
			return event.getNewStep();

		default:
			return event.getNewStep();
		}

	}

	public void handleFileUpload(FileUploadEvent event) {
		this.fileLayout.setXlsxStreams(event.getFile().getContent());
		FacesUtils.info(null, "Sucesso", "Arquivo salvo para leitura", "growl");
	}

	public ProductXlsxFileReadLayout getFileLayout() {
		return fileLayout;
	}

	public List<ProductXlsxFileReadProjection> getProductsProjection() {
		return productsProjection;
	}

}
