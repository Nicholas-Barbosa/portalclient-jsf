package com.portal.client.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;

import com.portal.client.dto.XlsxProductFileReadLayout;
import com.portal.client.dto.ProductImporterExtractedData;
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

	private XlsxProductFileReadLayout fileLayout;

	private List<ProductImporterExtractedData> productsProjection;

	public ProductFileImportComponent() {
		fileLayout = new XlsxProductFileReadLayout();
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
			importer.run(fileLayout);
//			FacesUtils.ajaxUpdate("dtProjection");
			FacesUtils.executeScript("PF('dlgLoading').hide()");
			
			return event.getNewStep();

		default:
			return event.getNewStep();
		}

	}

	public void handleFileUpload(FileUploadEvent event) {
		this.fileLayout.setXlsxStreams(event.getFile().getContent());
		FacesUtils.info(null, "Sucesso", "Arquivo salvo para leitura", "growl");
	}

	public XlsxProductFileReadLayout getFileLayout() {
		return fileLayout;
	}

	public List<ProductImporterExtractedData> getProductsProjection() {
		return productsProjection;
	}

}
