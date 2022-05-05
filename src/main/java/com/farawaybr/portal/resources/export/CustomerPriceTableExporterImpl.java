package com.farawaybr.portal.resources.export;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.poi.ss.usermodel.CellType;

import com.farawaybr.portal.microsoft.excel.CellAttribute;
import com.farawaybr.portal.microsoft.excel.RowObject;
import com.farawaybr.portal.microsoft.excel.CellAttribute.CellAttributeBuilder;
import com.farawaybr.portal.microsoft.excel.writer.XssfWriter;
import com.farawaybr.portal.vo.Customer;
import com.farawaybr.portal.vo.ProductPriceData;

@ApplicationScoped
public class CustomerPriceTableExporterImpl implements CustomerPriceTableExporter {

	@Inject
	private XssfWriter writer;

	@Override
	public byte[] toExcel(Customer customer) {
		List<RowObject> rows = new ArrayList<>();
		final AtomicInteger rowCounter = new AtomicInteger(3);
		RowObject customerHeader = new RowObject(0, CellAttributeBuilder.of(0, "Cliente", "Tabela"));
		RowObject customerDetails = new RowObject(1,
				CellAttributeBuilder.of(0, customer.getCode(), customer.getPriceTableCode()));

		RowObject headers = new RowObject(2, CellAttributeBuilder.of(0, "Produto", "Descrição", "Linha", "NCM", "Valor",
				"ST", "Bruto", "Aplicação"));
		rows.add(customerHeader);
		rows.add(customerDetails);
		rows.add(headers);
		rows.addAll(customer.getPriceTable().getProducts().parallelStream().map(product -> {
			ProductPriceData value = product.getPriceData();

			List<CellAttribute> attributes = new ArrayList<>();
			attributes.add(CellAttributeBuilder.of(0, product.getCommercialCode(), CellType.STRING));
			attributes.add(CellAttributeBuilder.of(1, product.getDescription(), CellType.STRING));
			attributes.add(CellAttributeBuilder.of(2, product.getLine(), CellType.STRING));
			attributes.add(CellAttributeBuilder.of(3, product.getNcm(), CellType.STRING));
			attributes.add(CellAttributeBuilder.ofNumber(4, value.getUnitValue().doubleValue()));
			attributes.add(CellAttributeBuilder.ofNumber(5, value.getUnitStValue().doubleValue()));
			attributes.add(CellAttributeBuilder.ofNumber(6, value.getUnitGrossValue().doubleValue()));
			attributes
					.add(CellAttributeBuilder.of(7, product.getProductTechDetail().getApplication(), CellType.STRING));
			return new RowObject(rowCounter.getAndIncrement(), attributes);
		}).collect(CopyOnWriteArrayList::new, List::add, List::addAll));

		return writer.write("tabela-de-preços", rows);
	}

}
