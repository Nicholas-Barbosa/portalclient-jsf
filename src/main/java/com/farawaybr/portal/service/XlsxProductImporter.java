package com.farawaybr.portal.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.poi.ss.usermodel.CellType;

import com.farawaybr.portal.dto.BatchProductSearchDataWrapper;
import com.farawaybr.portal.dto.ProductFileReadLayout;
import com.farawaybr.portal.dto.ProductImportXlsxCustomizedRow;
import com.farawaybr.portal.dto.ProductImportXlsxLayout;
import com.farawaybr.portal.dto.ProductImporterExtractedData;
import com.farawaybr.portal.dto.ProductToFind;
import com.farawaybr.portal.dto.XlsxProductFileReadLayout;
import com.farawaybr.portal.exception.MismatchCellTypeExceptions;
import com.farawaybr.portal.exception.MismatchCellTypeExceptions.MismatchCellTypeException;
import com.farawaybr.portal.microsoft.excel.CellAttribute;
import com.farawaybr.portal.microsoft.excel.RowObject;
import com.farawaybr.portal.microsoft.excel.reader.CellReadPolicy;
import com.farawaybr.portal.microsoft.excel.reader.XssfReader;
import com.farawaybr.portal.regex.RegexUtils;
import com.farawaybr.portal.repository.ProductRepository;

@ApplicationScoped
public class XlsxProductImporter implements ProductImporter {

	@Inject
	private XssfReader xssReader;

	@Inject
	private ProductRepository productRepository;

	@Override
	public List<ProductImporterExtractedData> extractData(ProductFileReadLayout layout) {
		XlsxProductFileReadLayout xlsxLayout = (XlsxProductFileReadLayout) layout;
		try {
			List<RowObject> rows = xssReader.read(xlsxLayout.getXlsxStreams(), xlsxLayout.getOffSetCellForProductCode(),
					xlsxLayout.getOffSetCellForProductQuantity(), CellReadPolicy.FIRST_SECOND,
					xlsxLayout.getInitPosition(), xlsxLayout.getLastPosition());
			List<? extends Object> collectionObj = rows.parallelStream().map(r -> {
				try {
					return this.of(r, xlsxLayout.getOffSetCellForProductCode(),
							xlsxLayout.getOffSetCellForProductQuantity());
				} catch (MismatchCellTypeException e) {
					return e;
				}
			}).distinct().collect(CopyOnWriteArrayList::new, List::add, List::addAll);

			if (collectionObj.parallelStream().anyMatch(o -> o instanceof MismatchCellTypeException)) {
				throw new MismatchCellTypeExceptions(collectionObj.parallelStream()
						.filter(o -> o instanceof MismatchCellTypeException).map(o -> (MismatchCellTypeException) o)
						.collect(CopyOnWriteArrayList::new, List::add, List::addAll));
			}
			return collectionObj.parallelStream().filter(obj -> obj instanceof ProductImporterExtractedData)
					.map(obj -> (ProductImporterExtractedData) obj)
					.collect(CopyOnWriteArrayList::new, List::add, List::addAll);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public BatchProductSearchDataWrapper parseData(List<ProductImporterExtractedData> datas, String customerCode,
			String customerStore) {
		// TODO Auto-generated method stub
		return productRepository.batchProductSearch(customerCode, customerStore,
				datas.stream()
						.map(extractedData -> new ProductToFind(extractedData.getCode(), extractedData.getQuantity()))
						.collect(Collectors.toSet()));
	}

	private ProductImporterExtractedData of(RowObject row, int offsetForCode, int offSetForQuantity) {
		Map<Integer, CellAttribute> attributes = row.getCellAttributes().stream()
				.collect(Collectors.toMap(k -> k.getCellOffset(), v -> v));
		CellAttribute codeCell = attributes.get(offsetForCode);
		CellAttribute quantityCell = attributes.get(offSetForQuantity);
		if (codeCell != null && quantityCell != null) {
			String code;
			switch (codeCell.getCellType()) {
			case STRING:
				code = (String) codeCell.getValue();
				break;

			default:
				throw new MismatchCellTypeException(row.getOffset(), codeCell, CellType.STRING);
			}

			Object quantityObj = quantityCell.getValue();
			if (quantityObj == null)
				return new ProductImporterExtractedData((String) code, 1);

			switch (quantityCell.getCellType()) {
			case NUMERIC:
				return new ProductImporterExtractedData((String) code, ((Double) quantityObj).intValue());

			default:
				try {
					String quantityStr = RegexUtils.removeAllChars((String) quantityObj);
					return new ProductImporterExtractedData((String) codeCell.getValue(),
							Double.valueOf(quantityStr).intValue());
				} catch (NumberFormatException e) {
					throw new MismatchCellTypeException(row.getOffset(), quantityCell, CellType.NUMERIC);
				}
			}
		}

		throw new IllegalArgumentException(
				codeCell == null && quantityCell == null ? "Code cell and Quantity cell not found"
						: quantityCell == null ? "Quantity cell not found" : "Code cell not found");
	}

	@Override
	public List<RowObject> extractData(byte[] xlsxstreams) {
		// TODO Auto-generated method stub
		try {
			return xssReader.read(xlsxstreams, 0, 0,CellReadPolicy.ALL,0,-1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<ProductImportXlsxCustomizedRow> customizeExtractedData(ProductImportXlsxLayout layout) {
		// TODO Auto-generated method stub

		return null;
	}
}
