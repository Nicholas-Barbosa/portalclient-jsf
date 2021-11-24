package com.portal.client.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.portal.client.dto.ProductFileReadLayout;
import com.portal.client.dto.ProductImporterExtractedData;
import com.portal.client.dto.XlsxProductFileReadLayout;
import com.portal.client.microsoft.excel.RowObject;
import com.portal.client.microsoft.excel.reader.XssfReader;
import com.portal.client.service.crud.ProductService;
import com.portal.client.vo.Product;

@ApplicationScoped
public class XlsxProductImporter extends ProductImporter {

	@Inject
	private XssfReader xssReader;

	@Inject
	private ProductService productService;

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
	List<Product> parseData(List<ProductImporterExtractedData> datas) {
		// TODO Auto-generated method stub
		
		return null;
	}

	private ProductImporterExtractedData of(RowObject row, int offsetForCode, int offSetForQuantity) {
		Map<Integer, Object> attributes = row.getCellAttributes().stream()
				.collect(Collectors.toMap(k -> k.getCellOffset(), v -> v.getValue()));
		String code = (String) attributes.get(offsetForCode);
		Double quantity = (Double) attributes.get(offSetForQuantity);
		return new ProductImporterExtractedData(code, quantity == null ? 0 : quantity.intValue());
	}
}
