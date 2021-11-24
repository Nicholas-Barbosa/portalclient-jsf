package com.portal.client.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.poi.ss.usermodel.CellType;

import com.portal.client.dto.BatchProductSearchDataWrapper;
import com.portal.client.dto.ProductFileReadLayout;
import com.portal.client.dto.ProductImporterExtractedData;
import com.portal.client.dto.XlsxProductFileReadLayout;
import com.portal.client.exception.MismatchCellTypeException;
import com.portal.client.microsoft.excel.CellAttribute;
import com.portal.client.microsoft.excel.RowObject;
import com.portal.client.microsoft.excel.reader.XssfReader;
import com.portal.client.regex.RegexUtils;

@ApplicationScoped
public class XlsxProductImporter extends ProductImporter {

	@Inject
	private XssfReader xssReader;

//	@Inject
//	private ProductRepository productRepository;

	@Override
	List<ProductImporterExtractedData> extractData(ProductFileReadLayout layout) {
		try {
			XlsxProductFileReadLayout xlsxLayout = (XlsxProductFileReadLayout) layout;
			List<RowObject> rows = xssReader.read(xlsxLayout.getXlsxStreams(), xlsxLayout.getInitPosition(),
					xlsxLayout.getLastPosition());
			return rows.parallelStream()
					.map(r -> this.of(r, xlsxLayout.getOffSetCellForProductCode(),
							xlsxLayout.getOffSetCellForProductQuantity()))
					.distinct().collect(CopyOnWriteArrayList::new, List::add, List::addAll);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	BatchProductSearchDataWrapper parseData(List<ProductImporterExtractedData> datas, String customerCode,
			String customerStore) {
		// TODO Auto-generated method stub
//		return productRepository.batchProductSearch(customerCode, customerStore,
//				datas.stream()
//						.map(extractedData -> new ProductToFind(extractedData.getCode(), extractedData.getQuantity()))
//						.collect(Collectors.toSet()));
		return null;
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
				String quantityStr = RegexUtils.removeAllChars(((String) quantityObj));
				System.out.println("Quantity str " + quantityStr);
				return new ProductImporterExtractedData((String) codeCell.getValue(),
						Double.valueOf(quantityStr).intValue());
			} catch (NumberFormatException e) {
				throw new MismatchCellTypeException(row.getOffset(), quantityCell, CellType.NUMERIC);
			}
		}

	}
}
