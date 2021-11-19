package com.portal.client.service.export;

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
import com.portal.client.microsoft.excel.writer.WriteCellAttribute.WriteCellAttributeBuilder;
import com.portal.client.microsoft.excel.writer.XssfWriter;
import com.portal.client.util.MathUtils;
import com.portal.client.vo.Budget;
import com.portal.client.vo.Item;
import com.portal.client.vo.Product;
import com.portal.client.vo.ProductPriceData;

@ApplicationScoped
public class OrderExcelCalculationCheckBudgetExporter {

	@Inject
	private XssfWriter xssfWriter;

	private final Map<String, Integer> columnsPositions = new ConcurrentHashMap<>();

	public OrderExcelCalculationCheckBudgetExporter() {
		this.intiColumnsPositions();
	}

	public byte[] export(Budget budget) {
		List<RowObject> rowObjects = new CopyOnWriteArrayList<>();
		rowObjects.add(createRowForColumns());

		final AtomicInteger rowPos = new AtomicInteger(1);

		budget.getItems().parallelStream().forEach(item -> {
			rowObjects.add(new RowObject(rowPos.getAndIncrement(), createPriceCells(item)));
		});
		return xssfWriter.write("conferência-cálculos", rowObjects);
	}

	private List<WriteCellAttribute> createPriceCells(Item item) {
		Product product = item.getProduct();
		ProductPriceData priceData = product.getPriceData();
		List<WriteCellAttribute> cells = new LinkedList<>();
		cells.add(WriteCellAttributeBuilder.of(columnsPositions.get("productCode"), product.getCommercialCode()));
		cells.add(WriteCellAttributeBuilder.of(columnsPositions.get("line"), product.getLine()));
		cells.add(WriteCellAttributeBuilder.of(columnsPositions.get("quantity"), priceData.getQuantity()));
		cells.add(WriteCellAttributeBuilder.ofNumber(columnsPositions.get("unitValue"), priceData.getUnitValue()));
		cells.add(WriteCellAttributeBuilder.ofNumber(columnsPositions.get("totalValue"), priceData.getTotalValue()));
		cells.add(
				WriteCellAttributeBuilder.ofNumber(columnsPositions.get("totalStValue"), priceData.getTotalStValue()));
		cells.add(WriteCellAttributeBuilder.ofNumber(columnsPositions.get("totalGrossValueWithoutDiscount"),
				priceData.getTotalGrossValue()));

		BigDecimal lineDiscountValueOnTheTotal = MathUtils.findHwMuchXPercentCorrespondsOverWholeValue(
				BigDecimal.valueOf(priceData.getDiscountData().getDiscount()), priceData.getTotalGrossValue());

		BigDecimal globalDiscountValueOnTheTotal = MathUtils.findHwMuchXPercentCorrespondsOverWholeValue(
				BigDecimal.valueOf(priceData.getDiscountData().getBudgetGlobalDiscount()),
				priceData.getTotalGrossValue());

		cells.add(WriteCellAttributeBuilder.ofNumber(columnsPositions.get("globalDiscount"),
				priceData.getDiscountData().getBudgetGlobalDiscount()));

		cells.add(WriteCellAttributeBuilder.ofNumber(columnsPositions.get("globalDiscountValue"),
				globalDiscountValueOnTheTotal));
		cells.add(WriteCellAttributeBuilder.ofNumber(columnsPositions.get("totalGrossValueAfterGlobalDiscount"),
				MathUtils.addValueByPercentage(priceData.getDiscountData().getDiscount(),
						priceData.getTotalGrossValue())));
		cells.add(WriteCellAttributeBuilder.ofNumber(columnsPositions.get("lineDiscount"),
				priceData.getDiscountData().getDiscount()));

		cells.add(WriteCellAttributeBuilder.ofNumber(columnsPositions.get("lineDiscountValue"),
				lineDiscountValueOnTheTotal));
		cells.add(WriteCellAttributeBuilder.ofNumber(columnsPositions.get("totalGrossValue"),
				priceData.getDiscountData().getTotalGrossValue()));
		return cells;
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
		columnsPositions.put("totalGrossValueAfterGlobalDiscount", 9);
		columnsPositions.put("lineDiscount", 10);
		columnsPositions.put("lineDiscountValue", 11);
		columnsPositions.put("totalGrossValue", 12);
	}
}
