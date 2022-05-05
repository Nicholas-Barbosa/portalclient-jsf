package com.farawaybr.portal.jsf.controller.components;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;

import com.farawaybr.portal.dto.ProductImporterExtractedData;
import com.farawaybr.portal.dto.XlsxProductFileReadLayout;
import com.farawaybr.portal.exception.MismatchCellTypeExceptions;
import com.farawaybr.portal.exception.ProductsNotFoundException;
import com.farawaybr.portal.exception.MismatchCellTypeExceptions.MismatchCellTypeException;
import com.farawaybr.portal.exceptionhandler.netowork.NetworkExceptionJoinPointCut;
import com.farawaybr.portal.jsf.controller.ProductFileImportObserver;
import com.farawaybr.portal.resources.export.ProductsImportComponentNotFoundCommandExporter;
import com.farawaybr.portal.service.ProductImporter;
import com.farawaybr.portal.util.jsf.FacesUtils;
import com.farawaybr.portal.vo.WrapperProduct404Error.Product404Error;

@ViewScoped
@Named
@NetworkExceptionJoinPointCut
public class ProductFileImportComponent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6376956116588422044L;

	@Inject
	private ProductImporter importer;

	private XlsxProductFileReadLayout fileLayout;

	private List<ProductImporterExtractedData> extractedData;

	private List<MismatchCellTypeException> mismatchCelltypeExceptions;

	private Product404Error[] productsNotFound;

	@Inject
	private ProductsImportComponentNotFoundCommandExporter exporter;

	public ProductFileImportComponent() {
		this.setDefaultFileLayoutPositions();

	}

	public void exportXlsxProductsNotFound() {
		FacesUtils.prepareResponseForDownloadOfStreams("produtos-inconsistentes.xlsx",
				exporter.execute(productsNotFound),
				"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	}

	public void confirm(String customerCode, String customerStore, ProductFileImportObserver observer) {
		try {
			observer.onConfirm(importer.parseData(extractedData, customerCode, customerStore));
			this.setDefaultFileLayoutPositions();
			this.fileLayout.setXlsxStreams(null);
			this.mismatchCelltypeExceptions = null;
			this.extractedData = null;
		} catch (ProductsNotFoundException e) {
			productsNotFound = e.getProducts();
			extractedData.removeIf(p -> Arrays.stream(productsNotFound).parallel().anyMatch(
					pNotFound -> p.getCode().strip().equalsIgnoreCase(pNotFound.getProductIdentity().strip())));
			FacesUtils.addHeaderForResponse("products-not-found", true);
			if (extractedData.size() > 0)
				this.confirm(customerCode, customerStore, observer);
				
		}
	}

	public String onFlowProcess(FlowEvent event) {
		switch (event.getNewStep()) {
		case "setFileLayout":
			if (this.fileLayout.getXlsxStreams() == null || this.fileLayout.getXlsxStreams().length == 0) {
				FacesUtils.error(null, "Arquivo não detectado", "Realize o upload!", "growl");
				return "file";
			}
			return event.getNewStep();
		case "extractedData":
			try {
				this.fileLayout.setInitPosition(fileLayout.getInitPosition() - 1);
				this.fileLayout.setLastPosition(fileLayout.getLastPosition() == 0 ? 0
						: fileLayout.getLastPosition() == 1 ? 1 : fileLayout.getLastPosition() - 1);
				this.fileLayout.setOffSetCellForProductCode(fileLayout.getOffSetCellForProductCode() - 1);
				this.fileLayout.setOffSetCellForProductQuantity(fileLayout.getOffSetCellForProductQuantity() - 1);
				extractedData = importer.extractData(fileLayout);
				FacesUtils.executeScript("PF('dlgReadingFile').hide();updateExtractedDatas();");
				return event.getNewStep();
			} catch (MismatchCellTypeExceptions e) {
				mismatchCelltypeExceptions = e.getExceptions();
				FacesUtils.executeScript(
						"PF('dlgReadingFile').hide();PF('dlgMismatchExcpetions').show();updateMismatchs()");
				fileLayout.setXlsxStreams(null);
			} catch (IllegalArgumentException e) {
				switch (e.getMessage()) {
				case "java.lang.IllegalArgumentException: Code cell and Quantity cell not found":
					FacesUtils.error(null, "Células não encontradas",
							"A célula de código e quantidade não foram encontradas.Revise a configuração de layout!",
							"growl");
					break;

				case "java.lang.IllegalArgumentException: Quantity cell not found":
					FacesUtils.error(null, "Célula não encontrada",
							"A célula de quantidade não foi encontrada.Revise a configuração de layout!", "growl");
					break;
				case "java.lang.IllegalArgumentException: Code cell not found":
					FacesUtils.error(null, "Célula não encontrada",
							"A célula de código não foi encontrada.Revise a configuração de layout!", "growl");
					break;

				}
				FacesUtils.executeScript("PF('dlgReadingFile').hide();");
				return "setFileLayout";
			}
			return "file";

		default:
			return event.getNewStep();
		}

	}

	public void removeExtractedData(ProductImporterExtractedData data) {
		this.extractedData.remove(data);
	}

	private final void setDefaultFileLayoutPositions() {
		fileLayout = new XlsxProductFileReadLayout();
		fileLayout.setInitPosition(1);
		fileLayout.setOffSetCellForProductCode(1);
		fileLayout.setOffSetCellForProductQuantity(2);
	}

	public void handleFileUpload(FileUploadEvent event) {
		this.fileLayout.setXlsxStreams(event.getFile().getContent());
		FacesUtils.info(null, "Sucesso", "Arquivo salvo para leitura", "growl");
	}

	public XlsxProductFileReadLayout getFileLayout() {
		return fileLayout;
	}

	public List<ProductImporterExtractedData> getExtractedData() {
		return extractedData;
	}

	public List<MismatchCellTypeException> getMismatchCelltypeExceptions() {
		return mismatchCelltypeExceptions;
	}

	public Product404Error[] getProductsNotFound() {
		return productsNotFound;
	}

	public void setProductsNotFound(Product404Error[] productsNotFound) {
		this.productsNotFound = productsNotFound;
	}
}
