package com.portal.jasper.service;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.portal.java.resources.export.ExportType;
import com.portal.java.resources.export.report.ReportService;
import com.portal.java.resources.export.report.jasper.OrderReportImpl;
import com.portal.java.resources.export.report.jasper.OrderJasperReport.OrderItemJasper;
import com.portal.java.resources.export.report.jasper.OrderJasperReport.CustomerJasperReportDTO;
import com.portal.java.resources.export.report.jasper.OrderJasperReport;
import com.portal.java.resources.export.report.jasper.OrderReport;

class BudgetReportImplTest {

	private final OrderItemJasper item = new OrderItemJasper("AX001", "LAMPADA", 10, BigDecimal.TEN, BigDecimal.TEN,
			BigDecimal.TEN, BigDecimal.TEN, BigDecimal.TEN, BigDecimal.TEN, BigDecimal.TEN);

	@Test
	void test() {
		OrderJasperReport budgetDTO = new OrderJasperReport(new BigDecimal(12.99), new BigDecimal(20.98),
				new BigDecimal(49.530000000000001136868377216160297393798828125),
				new CustomerJasperReportDTO("Nicholas", "Hawaii", "Pipeline", "Hawaii", "82828373", "ddd",
						"message"),
				Set.of(item));
		OrderReport budgetReport = new OrderReportImpl(new ReportService());
		byte[] bytes = budgetReport.export(budgetDTO, ExportType.PDF);
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
		OrderJasperReport budgetDTO = new OrderJasperReport(new BigDecimal(12.99), new BigDecimal(20.98),
				new BigDecimal(49.530000000000001136868377216160297393798828125), new CustomerJasperReportDTO(
						"Nicholas", "Hawaii", "Pipeline", "Hawaii", "nich", "ddd", "message"),
				Set.of(item));
		OrderReport budgetReport = new OrderReportImpl(new ReportService());
		byte[] bytes = budgetReport.export(budgetDTO, ExportType.EXCEL);

	}
}
