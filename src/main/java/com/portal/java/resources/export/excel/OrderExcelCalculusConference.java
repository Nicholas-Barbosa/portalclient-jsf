package com.portal.java.resources.export.excel;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.portal.java.dto.ItemValues;
import com.portal.java.dto.Order;
import com.portal.java.microsoft.excel.writer.WriteCellAttribute;
import com.portal.java.microsoft.excel.writer.WriteCellAttribute.WriteCellAttributeBuilder;
import com.portal.java.microsoft.excel.writer.WriteRowObject;
import com.portal.java.microsoft.excel.writer.XssfWriter;
import com.portal.java.util.MathUtils;

@ApplicationScoped
public class OrderExcelCalculusConference {

	@Inject
	private XssfWriter xssfWriter;

	public OrderExcelCalculusConference() {
		// TODO Auto-generated constructor stub
	}

	public OrderExcelCalculusConference(XssfWriter xssfWriter) {
		super();
		this.xssfWriter = xssfWriter;
	}

	public byte[] createWorkbook(Order order) {
		List<WriteRowObject> rowObjects = new CopyOnWriteArrayList<>();
		rowObjects.add(createRowForColumns());
		order.getItems().parallelStream().forEach(i -> {
			List<WriteCellAttribute> cells = new LinkedList<>();
			ItemValues values = i.getValues();

			cells.addAll(WriteCellAttributeBuilder.of(i.getProduct().getCommercialCode(),
					i.getProduct().getDescriptionType(), values.getQuantity()));
			cells.addAll(WriteCellAttributeBuilder.ofNumber(values.getUnitValueWithoutDiscount(),
					values.getTotalValueWithoutDiscount(), values.getTotalStValueWithoutDiscount(),
					values.getTotalGrossWithoutDiscount()));

			BigDecimal globalDiscValue = MathUtils.findHwMuchXPercentCorrespondsOverWholeValue(
					i.getBudgetGlobalDiscount(), values.getTotalGrossWithoutDiscount());
			cells.addAll(WriteCellAttributeBuilder.ofNumber(i.getBudgetGlobalDiscount(), globalDiscValue,
					values.getTotalGrossAfterGlobalDiscount(), i.getLineDiscount()));

			BigDecimal lineDiscValue = MathUtils.findHwMuchXPercentCorrespondsOverWholeValue(i.getLineDiscount(),
					values.getTotalGrossAfterGlobalDiscount());
			cells.add(WriteCellAttributeBuilder.ofNumber(lineDiscValue));
			cells.add(WriteCellAttributeBuilder.ofNumber(values.getTotalGrossValue()));

			rowObjects.add(new WriteRowObject(cells));
		});
		return xssfWriter.write(rowObjects);
	}

	private WriteRowObject createRowForColumns() {
		return new WriteRowObject(WriteCellAttributeBuilder.of("Cd.Comercial", "Linha", "qtd", "unit", "vlr", "st",
				"Preço", "Desc. Global %", "Desc R$", "Vlr.Liquido", "Desc. Linha %", "Desc R$", "Preço Final"));

	}

}
