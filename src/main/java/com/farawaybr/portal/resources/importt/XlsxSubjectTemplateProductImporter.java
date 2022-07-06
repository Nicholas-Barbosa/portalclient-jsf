package com.farawaybr.portal.resources.importt;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import javax.annotation.PreDestroy;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.inject.Inject;

import org.apache.poi.ss.usermodel.CellType;

import com.farawaybr.portal.dto.MultipleProductRowExcelData;
import com.farawaybr.portal.dto.ProductRowExcelData;
import com.farawaybr.portal.dto.ProductToFind;
import com.farawaybr.portal.exception.WrapperProductBatchSearchEndpointException;
import com.farawaybr.portal.regex.RegexUtils;
import com.farawaybr.portal.repository.ProductRepository;
import com.farawaybr.portal.resources.poi.microsoft.excel.CellAttribute;
import com.farawaybr.portal.resources.poi.microsoft.excel.reader.CellReadPolicy;
import com.farawaybr.portal.resources.poi.microsoft.excel.reader.XssfReader;
import com.farawaybr.portal.service.ObserverProductImporter;
import com.farawaybr.portal.service.SubjectProductImporter;
import com.farawaybr.portal.vo.WrapperProductBatchSearchEndpointError.ProductBatchSearchEndpointError;

@Stateful
public class XlsxSubjectTemplateProductImporter implements SubjectProductImporter, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ObserverProductImporter observer;

	@Inject
	private XssfReader xssfReader;

	private List<ProductRowExcelData> productsRowsExcelData;

	private String customerCode, customerStore;

	@Inject
	private ProductRepository productRepository;

	private short lastTemplatePhase = 0;

	private boolean containsProductsNotFoundFromLastTry;

	private List<ProductRowExcelData> productsNotFoundLastTry;

	@PreDestroy
	public void preDestroy() {
		
	}

	@Override
	public void extractData(byte[] xlsxstreams, int codeColumn, int quantityColumn, String customerCode,
			String customerStore) throws IOException {
		// TODO Auto-generated method stub
		if (lastTemplatePhase < 1) {
			lastTemplatePhase = 1;
			this.customerCode = customerCode;
			this.customerStore = customerStore;
			productsRowsExcelData = xssfReader
					.read(xlsxstreams, codeColumn, quantityColumn, CellReadPolicy.FIRST_SECOND, 0, -1).parallelStream()
					.map(rowObject -> {
						return new ProductRowExcelData(rowObject.getCell(codeColumn), rowObject.getCell(quantityColumn),
								rowObject.getOffset());
					}).collect(CopyOnWriteArrayList::new, List::add, List::addAll);
			return;
		}
		productsRowsExcelData.removeIf(p -> {
			Object obValue = p.getQuantityValue();
			return obValue instanceof Integer ? ((Integer) obValue) == -1 ? true : false : false;
		});

	}

	@Override
	public boolean isMismatchTypeCells() {
		if (lastTemplatePhase < 2) {
			lastTemplatePhase = 2;
			List<ProductRowExcelData> mismatchProductsQuantityType = this.productsRowsExcelData.parallelStream()
					.filter(this::isQuantityCellnotNumeric).collect(CopyOnWriteArrayList::new, List::add, List::addAll);
			if (mismatchProductsQuantityType != null && mismatchProductsQuantityType.size() > 0) {
				observer.onMismatchTypeCells(mismatchProductsQuantityType);
				mismatchProductsQuantityType = null;
				return true;
			}
			return false;
		}
		return false;
	}

	@Override
	public void findProducts() {
		// TODO Auto-generated method stub
		if (containsProductsNotFoundFromLastTry) {
			observer.onProductsNotFound(productsNotFoundLastTry);
			return;
		}
		Set<ProductToFind> productsToFind = null;
		lastTemplatePhase = 3;
		try {
			productsToFind = productsRowsExcelData.parallelStream().map(pRow -> {
				return new ProductToFind(pRow.getCode().getValue().toString(),
						Integer.parseInt(pRow.getQuantityValue().toString()));
			}).collect(ConcurrentSkipListSet::new, Set::add, Set::addAll);
			observer.onComplete(productRepository.batchProductSearch(customerCode, customerStore, productsToFind));
			this.lastTemplatePhase = 0;
			productsNotFoundLastTry = null;
		} catch (WrapperProductBatchSearchEndpointException e) {
			switch (e.getHttpStatus()) {
			case 404:
				List<String> products = Arrays.stream(e.getProducts())
						.map(ProductBatchSearchEndpointError::getProductIdentity).collect(Collectors.toList());
				observer.onProductsNotFound(this.getProductsExcelDataFromList(products));
				break;
			case 400:
				this.handleProductsNotFound(e);
				List<String> productsWithMultipleMismatch = Arrays.stream(e.getProducts()).filter(prr -> {
					return prr.getCause().contains("Multiplo");
				}).map(ProductBatchSearchEndpointError::getProductIdentity).collect(Collectors.toList());
				observer.onMismatchProductsMultiple(productsRowsExcelData.parallelStream()
						.filter(row -> productsWithMultipleMismatch.contains(row.getCode().getValue() + "")).map(p -> {
							StringBuilder errorCause = Arrays.stream(e.getProducts()).filter(
									prr -> prr.getProductIdentity().equalsIgnoreCase(p.getCode().getValue() + ""))
									.map(ProductBatchSearchEndpointError::getCause)
									.collect(StringBuilder::new, StringBuilder::append, StringBuilder::append);
							int correctMultiple = Integer
									.parseInt(errorCause.substring(errorCause.lastIndexOf(":") + 1).strip());
							return new MultipleProductRowExcelData(p, correctMultiple);
						}).collect(CopyOnWriteArrayList::new, List::add, List::addAll));
				break;

			}

		} finally {
			productsToFind = null;
		}
	}

	private List<ProductRowExcelData> getProductsExcelDataFromList(List<String> products) {
		return productsRowsExcelData.parallelStream().filter(row -> products.contains(row.getCode().getValue() + ""))
				.collect(CopyOnWriteArrayList::new, List::add, List::addAll);
	}

	private void handleProductsNotFound(WrapperProductBatchSearchEndpointException e) {
		List<String> productsNotFound = Arrays.stream(e.getProducts()).filter(prr -> {
			return prr.getCause().contains("Produto não encontrado");
		}).map(ProductBatchSearchEndpointError::getProductIdentity).collect(Collectors.toList());
		productsNotFoundLastTry = productsNotFound.size() > 0 ? productsRowsExcelData.parallelStream()
				.filter(p -> productsNotFound.contains(p.getCode().getValue() + ""))
				.collect(CopyOnWriteArrayList::new, List::add, List::addAll) : null;
		this.containsProductsNotFoundFromLastTry = productsNotFound.size() > 0;
	}

	private boolean isQuantityCellnotNumeric(ProductRowExcelData product) {
		CellAttribute cellToReview = product.getQuantity();
		switch (cellToReview.getCellType()) {
		case NUMERIC:
			cellToReview.setValue(Double.valueOf(cellToReview.getValue() + "").intValue());
			return false;
		default:
			try {
				String quantityStr = RegexUtils.removeAllChars((String) cellToReview.getValue());
				cellToReview.setValue(Double.valueOf(quantityStr).intValue());
				cellToReview.setCellType(CellType.NUMERIC);
				return false;
			} catch (NumberFormatException e) {
				return true;
			}
		}

	}

	@Override
	public void registerObserver(ObserverProductImporter observer) {
		// TODO Auto-generated method stub
		this.observer = observer;
	}

	@Override
	public void unRegisterObserver(ObserverProductImporter observer) {
		// TODO Auto-generated method stub
		this.observer = null;
	}

	@Override
	@Remove
	public void remove() {

	}

}
