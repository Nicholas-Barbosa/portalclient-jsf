package com.farawaybr.portal.jsf.controller.components;

import java.io.IOException;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.poi.ss.usermodel.CellType;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.RowEditEvent;

import com.farawaybr.portal.dto.BatchProductSearchDataWrapper.BatchProductSearchData;
import com.farawaybr.portal.dto.ExtractedDataPhase;
import com.farawaybr.portal.dto.ProductRowExcelData;
import com.farawaybr.portal.dto.ProductToFind;
import com.farawaybr.portal.dto.XlsxProductFileReadLayout;
import com.farawaybr.portal.exception.MismatchCellTypeExceptions;
import com.farawaybr.portal.exception.MismatchCellTypeExceptions.MismatchCellTypeException;
import com.farawaybr.portal.exceptionhandler.netowork.NetworkExceptionJoinPointCut;
import com.farawaybr.portal.jsf.controller.ProductFileImportComponentObserver;
import com.farawaybr.portal.regex.RegexUtils;
import com.farawaybr.portal.resources.export.ProductsImportComponentNotFoundCommandExporter;
import com.farawaybr.portal.resources.poi.microsoft.excel.CellAttribute;
import com.farawaybr.portal.resources.poi.microsoft.excel.RowObject;
import com.farawaybr.portal.resources.poi.microsoft.excel.reader.CellReadPolicy;
import com.farawaybr.portal.resources.poi.microsoft.excel.reader.XssfReader;
import com.farawaybr.portal.service.ObserverProductImporter;
import com.farawaybr.portal.service.ProductImporter;
import com.farawaybr.portal.service.SubjectProductImporter;
import com.farawaybr.portal.util.jsf.FacesUtils;
import com.farawaybr.portal.vo.WrapperProductBatchSearchEndpointError.ProductBatchSearchEndpointError;

@ViewScoped
@Named
@NetworkExceptionJoinPointCut
public class ProductFileImportComponent implements Serializable, ObserverProductImporter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6376956116588422044L;

	@Inject
	private ProductImporter importer;

	@Inject
	private SubjectProductImporter templateImporter;

	@Inject
	private XssfReader excelReader;

	private byte[] xlsxstreams;

	private XlsxProductFileReadLayout fileLayout;

	private List<MismatchCellTypeException> mismatchCelltypeExceptions;

	private ProductBatchSearchEndpointError[] productsNotFound;

	@Inject
	private ProductsImportComponentNotFoundCommandExporter exporter;

	private List<CellAttribute> excelpreviewData;

	private int[] avaliableColumns;

	private int codeColumn, quantityColumn;

	private List<CellAttribute> mismatchsForCellType;

	private short resolvedMismatchColumnsCounter, initialmismatchsForCellTypeSize;

	private final String resolvedMismatchColumnsMessageToFormat = "Da(s) {0} coluna(s) incompatível(is), {1} foram resolvida(s)";

	private String resolvedMismatchColumnsMessage;

	public ProductFileImportComponent() {
		this.setDefaultFileLayoutPositions();
	}

	@PostConstruct
	public void postDI() {
		templateImporter.registerObserver(this);
	}

	@PreDestroy
	public void preDestroy() {
		templateImporter.unRegisterObserver(this);
	}

	public void read(String customerCode, String customerStore) {
		try {
			System.out.println("customerCode " + customerCode + " " + customerStore);
			templateImporter.execute(xlsxstreams, codeColumn, quantityColumn, customerCode, customerStore);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void handleFileUpload(FileUploadEvent event) {
		this.xlsxstreams = event.getFile().getContent();
//		this.fileLayout = new XlsxProductFileReadLayout();
//		this.fileLayout.setXlsxStreams(xlsxstreams);
		this.previewExcelFile();
		avaliableColumns = this.excelpreviewData.parallelStream().mapToInt(CellAttribute::getCellOffset).distinct()
				.sorted().toArray();
		this.codeColumn = 0;
		this.quantityColumn = 1;
	}

	private void previewExcelFile() {
		try {
			excelpreviewData = excelReader.read(xlsxstreams, 0, 0, CellReadPolicy.ALL, 0, 5).parallelStream()
					.map(RowObject::getCellAttributes).flatMap(List::parallelStream)
					.collect(CopyOnWriteArrayList::new, List::add, List::addAll);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void onRowEdit(RowEditEvent<CellAttribute> event) {
		CellAttribute cellAttribute = event.getObject();
		if (RegexUtils.isOnlyNumbers(cellAttribute.getValue() + "")) {
			this.mismatchsForCellType.remove(cellAttribute);
			this.resolvedMismatchColumnsMessage = MessageFormat.format(resolvedMismatchColumnsMessageToFormat,
					initialmismatchsForCellTypeSize, ++this.resolvedMismatchColumnsCounter);
			FacesUtils.addHeaderForResponse("typesok", this.mismatchsForCellType.size() == 0);
			cellAttribute.setCellType(CellType.NUMERIC);
			if (Integer.parseInt(cellAttribute.getValue() + "") == -1) {
				cellAttribute.autoRemove();
				cellAttribute = null;
			}
			return;
		}
		FacesUtils.warn(null, "Digite um tipo adequado", null, "growl");
	}

	public void exportXlsxProductsNotFound() {
		FacesUtils.prepareResponseForDownloadOfStreams("produtos-inconsistentes.xlsx",
				exporter.execute(productsNotFound),
				"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	}

	public void confirm(String customerCode, String customerStore, ProductFileImportComponentObserver observer) {
//		try {
//			observer.onConfirm(importer.parseData(extractedData, customerCode, customerStore));
//			this.setDefaultFileLayoutPositions();
//			this.fileLayout.setXlsxStreams(null);
//			this.mismatchCelltypeExceptions = null;
//			this.extractedData = null;
//		} catch (ProductsNotFoundException e) {
//			productsNotFound = e.getProducts();
//			extractedData.removeIf(p -> Arrays.stream(productsNotFound).parallel().anyMatch(
//					pNotFound -> p.getCode().strip().equalsIgnoreCase(pNotFound.getProductIdentity().strip())));
//			FacesUtils.addHeaderForResponse("products-not-found", true);
//			if (extractedData.size() > 0)
//				this.confirm(customerCode, customerStore, observer);
//
//		}
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

	public void removeExtractedData(ProductImporterExcelExtractedData data) {
		this.extractedData.remove(data);
	}

	private final void setDefaultFileLayoutPositions() {
		fileLayout = new XlsxProductFileReadLayout();
		fileLayout.setInitPosition(1);
		fileLayout.setOffSetCellForProductCode(1);
		fileLayout.setOffSetCellForProductQuantity(2);
	}

	public XlsxProductFileReadLayout getFileLayout() {
		return fileLayout;
	}

	public List<ProductImporterExcelExtractedData> getExtractedData() {
		return extractedData;
	}

	public List<MismatchCellTypeException> getMismatchCelltypeExceptions() {
		return mismatchCelltypeExceptions;
	}

	public ProductBatchSearchEndpointError[] getProductsNotFound() {
		return productsNotFound;
	}

	public void setProductsNotFound(ProductBatchSearchEndpointError[] productsNotFound) {
		this.productsNotFound = productsNotFound;
	}

	public List<CellAttribute> getExcelpreviewData() {
		return excelpreviewData;
	}

	public int[] getAvaliableColumns() {
		return avaliableColumns;
	}

	public int getCodeColumn() {
		return codeColumn;
	}

	public void setCodeColumn(int codeColumn) {
		this.codeColumn = codeColumn;
	}

	public int getQuantityColumn() {
		return quantityColumn;
	}

	public void setQuantityColumn(int quantityColumn) {
		this.quantityColumn = quantityColumn;
	}

	@Override
	public void onExtractedData(ExtractedDataPhase jessionId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMismatchProductsMultiple(Set<BatchProductSearchData> products) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMismatchTypeCells(List<ProductRowExcelData> rows) {
		// TODO Auto-generated method stub
		this.mismatchsForCellType = rows.parallelStream().map(RowObject::getCellAttributes)
				.flatMap(List::parallelStream).filter(c -> c.getCellOffset() == quantityColumn)
				.collect(CopyOnWriteArrayList::new, List::add, List::addAll);
		this.initialmismatchsForCellTypeSize = (short) mismatchsForCellType.size();
		resolvedMismatchColumnsMessage = MessageFormat.format(resolvedMismatchColumnsMessageToFormat,
				initialmismatchsForCellTypeSize, "nenhuma");
		FacesUtils.addHeaderForResponse("mismatch", true);
	}

	public List<CellAttribute> getMismatchsForCellType() {
		return mismatchsForCellType;
	}

	public short getResolvedMismatchColumnsCounter() {
		return resolvedMismatchColumnsCounter;
	}

	public String getResolvedMismatchColumnsMessage() {
		return resolvedMismatchColumnsMessage;
	}


	@Override
	public void onProductsNotFound(Set<ProductToFind> productsToFind) {
		// TODO Auto-generated method stub

	}

	

}
