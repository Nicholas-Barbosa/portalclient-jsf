package com.farawaybr.portal.service.excelimporter;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateful;
import javax.inject.Inject;

import org.apache.poi.ss.usermodel.CellType;

import com.farawaybr.portal.dto.ProductToFind;
import com.farawaybr.portal.microsoft.excel.CellAttribute;
import com.farawaybr.portal.microsoft.excel.RowObject;
import com.farawaybr.portal.microsoft.excel.reader.CellReadPolicy;
import com.farawaybr.portal.microsoft.excel.reader.XssfReader;
import com.farawaybr.portal.regex.RegexUtils;
import com.farawaybr.portal.repository.ProductRepository;
import com.farawaybr.portal.service.ObserverProductImporter;
import com.farawaybr.portal.service.SubjectProductImporter;

@Stateful
public class XlsxSubjectTemplateProductImporter implements SubjectProductImporter, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ObserverProductImporter observer;

	@Inject
	private XssfReader xssfReader;

	private List<RowObject> extractedData;

	private List<RowObject> rowsMismatchcelltype;

	private int codeColumn, quantityColumn;

	@Inject
	private ProductRepository productRepository;

	@PostConstruct
	public void onCreated() {
		System.out.println("On cretaed");
	}

	@PreDestroy()
	public void onDestroy() {
		System.out.println("on destroy!");
	}

	@Override
	public void extractResData(byte[] xlsxstreams, int codeColumn, int quantityColumn) throws IOException {
		// TODO Auto-generated method stub
		this.codeColumn = codeColumn;
		this.quantityColumn = quantityColumn;
		extractedData = extractedData == null
				? xssfReader.read(xlsxstreams, codeColumn, quantityColumn, CellReadPolicy.FIRST_SECOND, 0, -1)
				: extractedData;
	}

	@Override
	public void findProducts() {
		// TODO Auto-generated method stub
		System.out.println("find products!");
		productRepository.batchProductSearch(null, null,
				extractedData.parallelStream()
						.map(r -> new ProductToFind(r.getCell(codeColumn).getValue().toString(),
								(Integer) r.getCell(quantityColumn).getValue()))
						.collect(ConcurrentSkipListSet::new, Set::add, Set::addAll));

	}

	@Override
	public boolean isMismatchProductsMultiple() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isMismatchTypeCells() {
		// TODO Auto-generated method stub
		this.rowsMismatchcelltype = this.extractedData.parallelStream().filter(this::isQuantityCellnotNumeric)
				.collect(CopyOnWriteArrayList::new, List::add, List::addAll);
		if (rowsMismatchcelltype != null && rowsMismatchcelltype.size() > 0) {
			observer.onMismatchTypeCells(rowsMismatchcelltype);
			return true;
		}
		return false;
	}

	private boolean isQuantityCellnotNumeric(RowObject row) {
		CellAttribute cellToReview = row.getCell(quantityColumn);
		switch (cellToReview.getCellType()) {
		case NUMERIC:
			return false;
		default:
			try {
				String quantityStr = RegexUtils.removeAllChars((String) cellToReview.getValue());
				row.setCellValue(quantityColumn, Double.valueOf(quantityStr).intValue());
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
	public void notifyObserver() {
		// TODO Auto-generated method stub

	}

}
