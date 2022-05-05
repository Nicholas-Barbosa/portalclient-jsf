package com.farawaybr.portal.resources.export;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.poi.ss.usermodel.CellType;

import com.farawaybr.portal.cdi.qualifier.Summary;
import com.farawaybr.portal.microsoft.excel.CellAttribute;
import com.farawaybr.portal.microsoft.excel.RowObject;
import com.farawaybr.portal.microsoft.excel.CellAttribute.CellAttributeBuilder;
import com.farawaybr.portal.microsoft.excel.writer.XssfWriter;
import com.farawaybr.portal.vo.Budget;
import com.farawaybr.portal.vo.Item;
import com.farawaybr.portal.vo.ProductPriceData;

@ApplicationScoped
@Summary
public class ExcelCalculationSummaryBudgetExporter implements BudgetExporter {

	@Inject
	private XssfWriter xssfWriter;

	private final Map<String, Integer> columnsPositions = new ConcurrentHashMap<>();

	public ExcelCalculationSummaryBudgetExporter() {
		this.initColumns();
	}

	@Override
	public byte[] export(Budget budget) {
		List<RowObject> rowObjects = new CopyOnWriteArrayList<>();
		rowObjects.add(createRowHeaders());

		final AtomicInteger rowPos = new AtomicInteger(1);

		budget.getItems().parallelStream().forEach(item -> {
			rowObjects.add(new RowObject(rowPos.getAndIncrement(), createPriceCells(item)));
		});
		return xssfWriter.write("conferência-cálculos", rowObjects);
	}

	private List<CellAttribute> createPriceCells(Item item) {
		Item product = item;
		ProductPriceData priceData = product.getPriceData();
		List<CellAttribute> cells = new LinkedList<>();
		addCellValue(cells, "productCode", product.getCommercialCode());
		addCellValue(cells, "line", product.getLine());
		addCellValue(cells, "quantity", priceData.getQuantity() + "");
		addCellValue(cells, "unitValue", priceData.getUnitValue().doubleValue());
		addCellValue(cells, "totalValue", priceData.getTotalValue().doubleValue());
		addCellValue(cells, "totalStValue", priceData.getTotalStValue().doubleValue());
		addCellValue(cells, "totalGrossValueWithoutDiscount", priceData.getTotalGrossValue().doubleValue());
		createDiscountCells(cells, priceData);

		return cells;
	}

	private RowObject createRowHeaders() {
		return new RowObject(0, CellAttributeBuilder.of(0, "Cd.Comercial", "Linha", "Quantidade", "unit", "valor", "ST",
				"Preço", "Desc. Global %", "Desc R$", "Vlr.Liquido", "Desc. Linha %", "Desc R$", "Preço Final"));

	}

	private void initColumns() {
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

	private void createDiscountCells(List<CellAttribute> cells, ProductPriceData data) {
		this.addCellValue(cells, "globalDiscount", 0f);
		this.addCellValue(cells, "globalDiscountValue", 0f);
		this.addCellValue(cells, "totalGrossValueAfterGlobalDiscount", 0f);
		this.addCellValue(cells, "lineDiscount", 0f);
		this.addCellValue(cells, "lineDiscountValue", 0f);
		this.addCellValue(cells, "totalGrossValue", data.getTotalGrossValue().doubleValue());
	}

	private void addCellValue(List<CellAttribute> cells, String columnKey, Object value) {
		int column = columnsPositions.get(columnKey);
		cells.add(value instanceof Double ? CellAttributeBuilder.ofNumber(column, (double) value)
				: CellAttributeBuilder.of(column, value, CellType.STRING));
	}
}
