package com.farawaybr.portal.jsf.controller.components;

import java.io.IOException;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.poi.ss.usermodel.CellType;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;

import com.farawaybr.portal.dto.BatchProductSearchDataWrapper;
import com.farawaybr.portal.dto.MultipleProductRowExcelData;
import com.farawaybr.portal.dto.ProductRowExcelData;
import com.farawaybr.portal.exceptionhandler.netowork.NetworkExceptionJoinPointCut;
import com.farawaybr.portal.jsf.controller.ProductFileImportComponentObserver;
import com.farawaybr.portal.regex.RegexUtils;
import com.farawaybr.portal.resources.poi.microsoft.excel.CellAttribute;
import com.farawaybr.portal.resources.poi.microsoft.excel.RowObject;
import com.farawaybr.portal.resources.poi.microsoft.excel.reader.CellReadPolicy;
import com.farawaybr.portal.resources.poi.microsoft.excel.reader.XssfReader;
import com.farawaybr.portal.service.ObserverProductImporter;
import com.farawaybr.portal.service.SubjectProductImporter;
import com.farawaybr.portal.util.jsf.FacesUtils;

@org.omnifaces.cdi.ViewScoped
@Named
@NetworkExceptionJoinPointCut
public class ProductFileImportComponentV2 implements Serializable, ObserverProductImporter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6376956116588422044L;

	@Inject
	private SubjectProductImporter templateImporter;

	@Inject
	private XssfReader excelReader;

	private byte[] xlsxstreams;

	private List<ProductRowExcelData> productsWithProblems;

	private List<CellAttribute> excelpreviewData;

	private int[] avaliableColumns;

	private int codeColumn, quantityColumn;

	private short resolvedMismatchColumnsCounter, productsWithProblemSize;

	private final String problemMessage = "Da(s) {0} coluna(s) problemáticas(is), {1} foram resolvida(s)";

	private String resolvedMismatchColumnsMessage;

	private String mismatch;

	private BatchProductSearchDataWrapper batchProductWrapper;

	@PostConstruct
	public void postDI() {
		templateImporter.registerObserver(this);
	}

	@PreDestroy
	public void preDestroy() {
		templateImporter.unRegisterObserver(this);
		templateImporter.remove();
	}

	public void confirm(ProductFileImportComponentObserver observer) {
		observer.onConfirm(batchProductWrapper);
		batchProductWrapper = null;
	}

	public void read(String customerCode, String customerStore) {
		try {
			this.resolvedMismatchColumnsCounter = 0;
			templateImporter.execute(xlsxstreams, codeColumn, quantityColumn, customerCode, customerStore);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.avaliableColumns = null;
			this.excelpreviewData = null;
		}
	}

	public void handleFileUpload(FileUploadEvent event) {
		this.xlsxstreams = event.getFile().getContent();
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

	@Override
	public void onComplete(BatchProductSearchDataWrapper batchProduct) {
		// TODO Auto-generated method stub
		this.batchProductWrapper = batchProduct;
		FacesUtils.addHeaderForResponse("onComplete", true);
	}

	@Override
	public void onMismatchProductsMultiple(List<MultipleProductRowExcelData> products) {
		// TODO Auto-generated method stub
		this.mismatch = "multiple";
		this.productsWithProblems = new ArrayList<>(products);
		this.productsWithProblemSize = (short) productsWithProblems.size();
		resolvedMismatchColumnsMessage = MessageFormat.format(problemMessage, productsWithProblemSize, "nenhuma");
		FacesUtils.addHeaderForResponse("mismatch", true);
	}

	@Override
	public void onMismatchTypeCells(List<ProductRowExcelData> rows) {
		// TODO Auto-generated method stub
		this.mismatch = "cellType";
		this.productsWithProblems = rows;
		this.productsWithProblemSize = (short) productsWithProblems.size();
		resolvedMismatchColumnsMessage = MessageFormat.format(problemMessage, productsWithProblemSize, "nenhuma");
		FacesUtils.addHeaderForResponse("mismatch", true);
	}

	@Override
	public void onProductsNotFound(List<ProductRowExcelData> products) {
		// TODO Auto-generated method stub
		this.mismatch = "productsNotfound";
		this.productsWithProblems = products;
		this.productsWithProblemSize = (short) productsWithProblems.size();
		products = null;
		FacesUtils.addHeaderForResponse("products-found", false);
		this.resolvedMismatchColumnsCounter = 0;
		resolvedMismatchColumnsMessage = MessageFormat.format(problemMessage, productsWithProblemSize, "nenhuma");
	}

	public void onRowEdit(RowEditEvent<ProductRowExcelData> event) {
		ProductRowExcelData product = event.getObject();
		switch (mismatch) {
		case "cellType":
			if (RegexUtils.isOnlyNumbers(product.getQuantityValue() + "")) {
				this.productsWithProblems.remove(product);
				this.resolvedMismatchColumnsMessage = MessageFormat.format(problemMessage, productsWithProblemSize,
						++this.resolvedMismatchColumnsCounter);
				product.getQuantity().setCellType(CellType.NUMERIC);
//				if (Integer.parseInt(product.getQuantityValue() + "") == -1) {
//					this.productsWithProblems.remove(product);
//					product = null;
//				}
				if (resolvedMismatchColumnsCounter == productsWithProblemSize) {
					FacesUtils.addHeaderForResponse("typesok", this.productsWithProblems.size() == 0);
				}
				product = null;
				return;
			}
			FacesUtils.warn(null, "Digite um tipo adequado", null, "growl");
			break;

		case "productsNotfound":
			this.productsWithProblems.remove(product);
			this.resolvedMismatchColumnsMessage = MessageFormat.format(problemMessage, productsWithProblemSize,
					++this.resolvedMismatchColumnsCounter);
			if (resolvedMismatchColumnsCounter == productsWithProblemSize) {
				FacesUtils.addHeaderForResponse("typesok", this.productsWithProblems.size() == 0);
			}
			break;
		case "multiple":
			MultipleProductRowExcelData multipleProduct = (MultipleProductRowExcelData) product;
			if (Integer.parseInt(multipleProduct.getQuantityValue() + "") % multipleProduct.getCorrectMultiple() == 0) {
				this.resolvedMismatchColumnsMessage = MessageFormat.format(problemMessage, productsWithProblemSize,
						++this.resolvedMismatchColumnsCounter);
				if (resolvedMismatchColumnsCounter == productsWithProblemSize) {
					FacesUtils.addHeaderForResponse("typesok", this.productsWithProblems.size() == 0);
				}
				FacesUtils.executeScript("updateMismatchTable()");
				return;
			}
			FacesUtils.warn(null, "Valor proibido",
					"Valor deve ser múltiplo de " + multipleProduct.getCorrectMultiple(), "growl");
			break;
		}

	}

	public List<ProductRowExcelData> getProductsWithProblems() {
		return productsWithProblems;
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

	public short getResolvedMismatchColumnsCounter() {
		return resolvedMismatchColumnsCounter;
	}

	public String getResolvedMismatchColumnsMessage() {
		return resolvedMismatchColumnsMessage;
	}

	public String getMismatch() {
		return mismatch;
	}

	public BatchProductSearchDataWrapper getBatchProductWrapper() {
		return batchProductWrapper;
	}
}
