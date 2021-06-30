package com.portal.java.resources.export.excel;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.portal.java.dto.Order;
import com.portal.java.microsoft.excel.writer.WriteCellAttribute;
import com.portal.java.microsoft.excel.writer.WriteCellAttribute.WriteCellAttributeBuilder;
import com.portal.java.microsoft.excel.writer.WriteRowObject;
import com.portal.java.microsoft.excel.writer.XssfWriter;

@ApplicationScoped
public class OrderExcelCalculusConference {

	@Inject
	private XssfWriter xssfWriter;

	public byte[] createWorkbook(Order order) {
//		OrderExcelCalculusConferenceProjection orderExcelCalculusConferenceProjection = new OrderExcelCalculusConferenceProjection();
//		orderExcelCalculusConferenceProjection.setGlobalDiscount(order.getGlobalDiscount().floatValue());
		List<WriteRowObject> rowObjects = new CopyOnWriteArrayList<>();
		order.getItems().parallelStream().forEach(i -> {
			WriteCellAttribute cell = new WriteCellAttribute(rowObjects, null);
		});
		return null;
	}

	private WriteRowObject createColumns() {
		return new WriteRowObject(WriteCellAttributeBuilder.of("Cd.Comercial", "qtd", "unit", "vlr", "st", "Preço",
				"Desc.Global", "Desc R$", "Vlr.Liquido", "Desc.Linha", "Desc R$", "Preço Final"));

	}
}
