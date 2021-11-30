package com.portal.client.controller.components;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;

import com.portal.client.controller.ProductFileImportObserver;
import com.portal.client.dto.ProductImporterExtractedData;
import com.portal.client.dto.XlsxProductFileReadLayout;
import com.portal.client.exception.MismatchCellTypeExceptions;
import com.portal.client.exception.MismatchCellTypeExceptions.MismatchCellTypeException;
import com.portal.client.exception.ProductsNotFoundException;
import com.portal.client.exceptionhandler.netowork.NetworkExceptionJoinPointCut;
import com.portal.client.service.ProductImporter;
import com.portal.client.util.jsf.FacesUtils;
import com.portal.client.vo.WrapperProduct404Error.Product404Error;

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

	public ProductFileImportComponent() {
		this.setDefaultFileLayoutPositions();

	}

	public void confirm(String customerCode, String customerStore, ProductFileImportObserver observer) {
		try {
			observer.onConfirm(importer.parseData(extractedData, customerCode, customerStore));
			this.setDefaultFileLayoutPositions();
			this.fileLayout.setXlsxStreams(null);
			this.mismatchCelltypeExceptions = null;
			this.extractedData = null;
			if (productsNotFound != null) {
				FacesUtils.addHeaderForResponse("products-not-found", true);
				return;
			}
		} catch (ProductsNotFoundException e) {
			productsNotFound = e.getProducts();
			extractedData.removeIf(p -> Arrays.stream(productsNotFound).parallel()
					.anyMatch(pNotFound -> p.getCode().equals(pNotFound.getProductIdentity())));
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
				FacesUtils.executeScript("PF('dlgLoading').hide();editScrollBodyMaxHeight('extractedDatas','35vh')");
				return event.getNewStep();
			} catch (MismatchCellTypeExceptions e) {
				mismatchCelltypeExceptions = e.getExceptions();
				FacesUtils
						.executeScript("PF('dlgLoading').hide();PF('dlgMismatchExcpetions').show();updateMismatchs()");
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
				FacesUtils.executeScript("PF('dlgLoading').hide();");
				return "setFileLayout";
			}
			return "file";

		default:
			return event.getNewStep();
		}

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
