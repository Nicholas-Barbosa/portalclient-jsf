package com.portal.jasper.service;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.portal.client.export.OrderExportType;
import com.portal.client.export.jasper.JasperService;
import com.portal.client.export.jasper.BudgetJasper;
import com.portal.client.export.jasper.BudgetReport;
import com.portal.client.export.jasper.BudgetReportImpl;
import com.portal.client.export.jasper.BudgetJasper.CustomerJasperReportDTO;
import com.portal.client.export.jasper.BudgetJasper.OrderItemJasper;

class BudgetReportImplTest {

	private final OrderItemJasper item = new OrderItemJasper("AX001", "LAMPADA", 10, BigDecimal.TEN, BigDecimal.TEN,
			BigDecimal.TEN, BigDecimal.TEN, BigDecimal.TEN, BigDecimal.TEN, BigDecimal.TEN);

	@Test
	void test() {
		BudgetJasper budgetDTO = new BudgetJasper(new BigDecimal(12.99), new BigDecimal(20.98),
				new BigDecimal(49.530000000000001136868377216160297393798828125),
				new CustomerJasperReportDTO("Nicholas", "Hawaii", "Pipeline", "Hawaii", "82828373", "ddd"),
				Set.of(item), "Mensagem");
		BudgetReport budgetReport = new BudgetReportImpl(new JasperService());
		byte[] bytes = budgetReport.export(budgetDTO, OrderExportType.PDF);
		System.out.println("lenght " + bytes.length);
		try (OutputStream out = new BufferedOutputStream(
				new FileOutputStream("C:\\Users\\nicho\\OneDrive\\Documentos\\filledReports\\budget.pdf"))) {
			out.write(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testExcel() {
		BudgetJasper budgetDTO = new BudgetJasper(new BigDecimal(12.99), new BigDecimal(20.98),
				new BigDecimal(49.530000000000001136868377216160297393798828125),
				new CustomerJasperReportDTO("Nicholas", "Hawaii", "Pipeline", "Hawaii", "nich", "ddd"), Set.of(item),
				"Message");
		BudgetReport budgetReport = new BudgetReportImpl(new JasperService());
		byte[] bytes = budgetReport.export(budgetDTO, OrderExportType.EXCEL);

	}
}
