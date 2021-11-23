package com.portal.client.resources.export;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.portal.client.dto.ProductPriceTableWrapper.ProductPriceTable;
import com.portal.client.microsoft.excel.RowObject;
import com.portal.client.microsoft.excel.writer.WriteCellAttribute;
import com.portal.client.microsoft.excel.writer.WriteCellAttribute.WriteCellAttributeBuilder;
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
		RowObject customerHeader = new RowObject(0, WriteCellAttributeBuilder.of(0, "Cliente", "Tabela"));
		RowObject customerDetails = new RowObject(1,
				WriteCellAttributeBuilder.of(0, customerCode, table.get(0).getCode()));

		RowObject headers = new RowObject(2,
				WriteCellAttributeBuilder.of(0, "Produto", "Descrição", "Linha", "Valor", "ST", "Bruto", "Aplicação"));
		rows.add(customerHeader);
		rows.add(customerDetails);
		rows.add(headers);
		rows.addAll(table.parallelStream().map(pTable -> {
			Product product = pTable.getProduct();
			ProductPriceData value = product.getPriceData();

			List<WriteCellAttribute> attributes = new ArrayList<>();
			attributes.add(WriteCellAttributeBuilder.of(0, product.getCommercialCode()));
			attributes.add(WriteCellAttributeBuilder.of(1, product.getDescription()));
			attributes.add(WriteCellAttributeBuilder.of(2, product.getLine()));
			attributes.add(WriteCellAttributeBuilder.ofNumber(3, value.getUnitValue().doubleValue()));
			attributes.add(WriteCellAttributeBuilder.ofNumber(4, value.getUnitStValue().doubleValue()));
			attributes.add(WriteCellAttributeBuilder.ofNumber(5, value.getUnitGrossValue().doubleValue()));
			attributes.add(WriteCellAttributeBuilder.of(6, product.getProductTechDetail().getApplication()));
			return new RowObject(rowCounter.getAndIncrement(), attributes);
		}).collect(CopyOnWriteArrayList::new, List::add, List::addAll));

		return writer.write("tabela-de-preços", rows);
	}

}
