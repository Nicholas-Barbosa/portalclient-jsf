package com.portal.client.service.export;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.portal.client.dto.ProductPriceTabletWrapper.ProductPriceTable;
import com.portal.client.dto.ProductValue;
import com.portal.client.microsoft.excel.RowObject;
import com.portal.client.microsoft.excel.writer.WriteCellAttribute;
import com.portal.client.microsoft.excel.writer.WriteCellAttribute.WriteCellAttributeBuilder;
import com.portal.client.microsoft.excel.writer.XssfWriter;
import com.portal.client.vo.Product;

@ApplicationScoped
public class ProductPriceTableExporterImpl implements ProductPriceTableExporter {

	@Inject
	private XssfWriter writer;

	@Override
	public byte[] toExcel(String customerCode, List<ProductPriceTable> table) {
		List<RowObject> rows = new ArrayList<>();
		final AtomicInteger rowCounter = new AtomicInteger(3);
		RowObject row1 = new RowObject(0, List.of(WriteCellAttributeBuilder.of(0, "Cliente")));
		RowObject row2 = new RowObject(1, List.of(WriteCellAttributeBuilder.of(0, customerCode)));
		RowObject header = new RowObject(2,
				WriteCellAttributeBuilder.of(0, "Tabela", "Produto", "Descrição", "Linha", "Valor", "ST", "Bruto"));
		rows.add(row1);
		rows.add(row2);
		rows.add(header);
		rows.addAll(table.parallelStream().map(pTable -> {
			Product product = pTable.getProduct();
			ProductValue value = product.getValue();

			List<WriteCellAttribute> attributes = new ArrayList<>();
			attributes.add(WriteCellAttributeBuilder.of(0, pTable.getCode()));
			attributes.add(WriteCellAttributeBuilder.of(1, product.getCommercialCode()));
			attributes.add(WriteCellAttributeBuilder.of(2, product.getDescription()));
			attributes.add(WriteCellAttributeBuilder.of(3, product.getLine()));
			attributes.add(WriteCellAttributeBuilder.ofNumber(4, value.getUnitValue()));
			attributes.add(WriteCellAttributeBuilder.ofNumber(5, value.getUnitStValue()));
			attributes.add(WriteCellAttributeBuilder.ofNumber(6, value.getUnitGrossValue()));
			return new RowObject(rowCounter.getAndIncrement(), attributes);
		}).collect(CopyOnWriteArrayList::new, List::add, List::addAll));

		return writer.write("tabela-de-preços", rows);
	}

}
