package com.portal.client.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.poi.ss.usermodel.CellType;

import com.portal.client.dto.BatchProductSearchDataWrapper;
import com.portal.client.dto.ProductFileReadLayout;
import com.portal.client.dto.ProductImporterExtractedData;
import com.portal.client.dto.ProductToFind;
import com.portal.client.dto.XlsxProductFileReadLayout;
import com.portal.client.exception.MismatchCellTypeExceptions;
import com.portal.client.exception.MismatchCellTypeExceptions.MismatchCellTypeException;
import com.portal.client.microsoft.excel.CellAttribute;
import com.portal.client.microsoft.excel.RowObject;
import com.portal.client.microsoft.excel.reader.XssfReader;
import com.portal.client.regex.RegexUtils;
import com.portal.client.repository.ProductRepository;

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
			List<RowObject> rows = xssReader.read(xlsxLayout.getXlsxStreams(), xlsxLayout.getInitPosition(),
					xlsxLayout.getLastPosition());
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
		Object quantityObj = quantityCell.getValue();

		if (quantityObj == null)
			return new ProductImporterExtractedData((String) codeCell.getValue(), 1);

		switch (quantityCell.getCellType()) {
		case NUMERIC:
			return new ProductImporterExtractedData((String) codeCell.getValue(), ((Double) quantityObj).intValue());

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
}
