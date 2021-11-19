package com.portal.client.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.portal.client.dto.BatchProductSearchDataWrapper.BatchProductData;
import com.portal.client.dto.ProductXlsxFileReadProjection;
import com.portal.client.dto.ProductToFind;
import com.portal.client.dto.ProductXlsxFileReadLayout;
import com.portal.client.exception.CustomerNotFoundException;
import com.portal.client.exception.ItemsNotFoundException;
import com.portal.client.microsoft.excel.RowObject;
import com.portal.client.microsoft.excel.reader.XssfReader;

@ApplicationScoped
public class XlsxProductImporter implements ProductImporter, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8496725734748305921L;
	private XssfReader xssReader;

	@Inject
	public XlsxProductImporter(XssfReader xssReader) {
		super();
		this.xssReader = xssReader;
	}

	@Override
	public List<ProductXlsxFileReadProjection> read(ProductXlsxFileReadLayout fileLayout) {
		try {
			List<RowObject> rows = xssReader.read(fileLayout.getXlsxStreams(), fileLayout.getInitPosition(),
					fileLayout.getLastPosition());
			return rows.parallelStream()
					.map(r -> this.of(r, fileLayout.getOffSetCellForProductCode(),
							fileLayout.getOffSetCellForProductQuantity()))
					.distinct().collect(CopyOnWriteArrayList::new, List::add, List::addAll);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public BatchProductData performBatchSearch(List<ProductXlsxFileReadProjection> items, String customerCode,
			String customerStore) throws CustomerNotFoundException, ItemsNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	private ProductXlsxFileReadProjection of(RowObject row, int offsetForCode, int offSetForQuantity) {
		Map<Integer, Object> attributes = row.getCellAttributes().stream()
				.collect(Collectors.toMap(k -> k.getCellOffset(), v -> v.getValue()));
		String code = (String) attributes.get(offsetForCode);
		Double quantity = (Double) attributes.get(offSetForQuantity);
		return new ProductXlsxFileReadProjection(code, quantity == null ? 0 : quantity.intValue());
	}

	private ProductToFind toItem(ProductXlsxFileReadProjection itemToConvert) {
		return new ProductToFind(itemToConvert.getCode(), itemToConvert.getQuantity());
	}

}
