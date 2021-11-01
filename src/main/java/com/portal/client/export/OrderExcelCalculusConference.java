package com.portal.client.export;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.portal.client.microsoft.excel.RowObject;
import com.portal.client.microsoft.excel.writer.WriteCellAttribute;
import com.portal.client.microsoft.excel.writer.XssfWriter;
import com.portal.client.microsoft.excel.writer.WriteCellAttribute.WriteCellAttributeBuilder;
import com.portal.client.util.MathUtils;
import com.portal.client.vo.ItemValue;
import com.portal.client.vo.Order;

@ApplicationScoped
public class OrderExcelCalculusConference {

	@Inject
	private XssfWriter xssfWriter;

	private final Map<String, Integer> columnsPositions = new ConcurrentHashMap<>();

	public OrderExcelCalculusConference() {
		this.intiColumnsPositions();
	}

	public byte[] createWorkbook(Order order) {
		List<RowObject> rowObjects = new CopyOnWriteArrayList<>();
		rowObjects.add(createRowForColumns());

		final AtomicInteger rowPos = new AtomicInteger(1);

		order.getItems().parallelStream().forEach(i -> {
			List<WriteCellAttribute> cells = new LinkedList<>();
			ItemValue values = i.getValue();

			cells.add(WriteCellAttributeBuilder.of(columnsPositions.get("productCode"),
					i.getProduct().getCommercialCode()));
			cells.add(WriteCellAttributeBuilder.of(columnsPositions.get("line"), i.getProduct().getLine()));
			cells.add(WriteCellAttributeBuilder.of(columnsPositions.get("quantity"), values.getQuantity()));
			cells.add(WriteCellAttributeBuilder.ofNumber(columnsPositions.get("unitValue"),
					values.getUnitValueWithoutDiscount()));
			cells.add(WriteCellAttributeBuilder.ofNumber(columnsPositions.get("totalValue"),
					values.getTotalValueWithoutDiscount()));
			cells.add(WriteCellAttributeBuilder.ofNumber(columnsPositions.get("totalStValue"),
					values.getTotalStValueWithoutDiscount()));
			cells.add(WriteCellAttributeBuilder.ofNumber(columnsPositions.get("totalGrossValueWithoutDiscount"),
					values.getTotalGrossWithoutDiscount()));

			BigDecimal globalDiscValue = MathUtils.findHwMuchXPercentCorrespondsOverWholeValue(
					values.getBudgetGlobalDiscount(), values.getTotalGrossWithoutDiscount());
			cells.add(WriteCellAttributeBuilder.ofNumber(columnsPositions.get("globalDiscount"),
					values.getBudgetGlobalDiscount()));
			cells.add(WriteCellAttributeBuilder.ofNumber(columnsPositions.get("globalDiscountValue"), globalDiscValue));
			cells.add(WriteCellAttributeBuilder.ofNumber(columnsPositions.get("totalGrossValueAfterDiscount"),
					values.getTotalGrossAfterGlobalDiscount()));
			cells.add(
					WriteCellAttributeBuilder.ofNumber(columnsPositions.get("lineDiscount"), values.getLineDiscount()));

			BigDecimal lineDiscValue = MathUtils.findHwMuchXPercentCorrespondsOverWholeValue(values.getLineDiscount(),
					values.getTotalGrossAfterGlobalDiscount());
			cells.add(WriteCellAttributeBuilder.ofNumber(columnsPositions.get("lineDiscountValue"), lineDiscValue));
			cells.add(WriteCellAttributeBuilder.ofNumber(columnsPositions.get("totalGrossValue"),
					values.getTotalGrossValue()));
			rowObjects.add(new RowObject(rowPos.getAndIncrement(), cells));
		});
		return xssfWriter.write("conferência-cálculos",rowObjects);
	}

	private RowObject createRowForColumns() {
		return new RowObject(0, WriteCellAttributeBuilder.of(0, "Cd.Comercial", "Linha", "Quantidade", "unit", "valor",
				"ST", "Preço", "Desc. Global %", "Desc R$", "Vlr.Liquido", "Desc. Linha %", "Desc R$", "Preço Final"));

	}

	private void intiColumnsPositions() {
		columnsPositions.put("productCode", 0);
		columnsPositions.put("line", 1);
		columnsPositions.put("quantity", 2);
		columnsPositions.put("unitValue", 3);
		columnsPositions.put("totalValue", 4);
		columnsPositions.put("totalStValue", 5);
		columnsPositions.put("totalGrossValueWithoutDiscount", 6);
		columnsPositions.put("globalDiscount", 7);
		columnsPositions.put("globalDiscountValue", 8);
		columnsPositions.put("totalGrossValueAfterDiscount", 9);
		columnsPositions.put("lineDiscount", 10);
		columnsPositions.put("lineDiscountValue", 11);
		columnsPositions.put("totalGrossValue", 12);
	}
}
