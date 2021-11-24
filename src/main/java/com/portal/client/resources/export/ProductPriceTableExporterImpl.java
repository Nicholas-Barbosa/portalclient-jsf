package com.portal.client.resources.export;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.poi.ss.usermodel.CellType;

import com.portal.client.dto.ProductPriceTableWrapper.ProductPriceTable;
import com.portal.client.microsoft.excel.CellAttribute;
import com.portal.client.microsoft.excel.CellAttribute.CellAttributeBuilder;
import com.portal.client.microsoft.excel.RowObject;
import com.portal.client.microsoft.excel.writer.XssfWriter;
import com.portal.client.vo.Product;
import com.portal.client.vo.ProductPriceData;

@ApplicationScoped
public class ProductPriceTableExporterImpl implements ProductPriceTableExporter {

	@Inject
	private XssfWriter writer;

	@Override
	public byte[] toExcel(String customerCode, List<ProductPriceTable> table) {
		List<RowObject> rows = new ArrayList<>();
		final AtomicInteger rowCounter = new AtomicInteger(3);
		RowObject customerHeader = new RowObject(0, CellAttributeBuilder.of(0, "Cliente", "Tabela"));
		RowObject customerDetails = new RowObject(1, CellAttributeBuilder.of(0, customerCode, table.get(0).getCode()));

		RowObject headers = new RowObject(2,
				CellAttributeBuilder.of(0, "Produto", "Descrição", "Linha", "Valor", "ST", "Bruto", "Aplicação"));
		rows.add(customerHeader);
		rows.add(customerDetails);
		rows.add(headers);
		rows.addAll(table.parallelStream().map(pTable -> {
			Product product = pTable.getProduct();
			ProductPriceData value = product.getPriceData();

			List<CellAttribute> attributes = new ArrayList<>();
			attributes.add(CellAttributeBuilder.of(0, product.getCommercialCode(), CellType.STRING));
			attributes.add(CellAttributeBuilder.of(1, product.getDescription(), CellType.STRING));
			attributes.add(CellAttributeBuilder.of(2, product.getLine(), CellType.STRING));
			attributes.add(CellAttributeBuilder.ofNumber(3, value.getUnitValue().doubleValue()));
			attributes.add(CellAttributeBuilder.ofNumber(4, value.getUnitStValue().doubleValue()));
			attributes.add(CellAttributeBuilder.ofNumber(5, value.getUnitGrossValue().doubleValue()));
			attributes
					.add(CellAttributeBuilder.of(6, product.getProductTechDetail().getApplication(), CellType.STRING));
			return new RowObject(rowCounter.getAndIncrement(), attributes);
		}).collect(CopyOnWriteArrayList::new, List::add, List::addAll));

		return writer.write("tabela-de-preços", rows);
	}

}
