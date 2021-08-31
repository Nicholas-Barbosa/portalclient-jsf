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
import com.portal.client.export.jasper.OrderJasper;
import com.portal.client.export.jasper.OrderReport;
import com.portal.client.export.jasper.OrderReportImpl;
import com.portal.client.export.jasper.OrderJasper.CustomerJasperReportDTO;
import com.portal.client.export.jasper.OrderJasper.OrderItemJasper;

class BudgetReportImplTest {

	private final OrderItemJasper item = new OrderItemJasper("AX001", "LAMPADA", 10, BigDecimal.TEN, BigDecimal.TEN,
			BigDecimal.TEN, BigDecimal.TEN, BigDecimal.TEN, BigDecimal.TEN, BigDecimal.TEN);

	@Test
	void test() {
		OrderJasper budgetDTO = new OrderJasper(new BigDecimal(12.99), new BigDecimal(20.98),
				new BigDecimal(49.530000000000001136868377216160297393798828125),
				new CustomerJasperReportDTO("Nicholas", "Hawaii", "Pipeline", "Hawaii", "82828373", "ddd"),
				Set.of(item), "Mensagem");
		OrderReport budgetReport = new OrderReportImpl(new JasperService());
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
		OrderJasper budgetDTO = new OrderJasper(new BigDecimal(12.99), new BigDecimal(20.98),
				new BigDecimal(49.530000000000001136868377216160297393798828125),
				new CustomerJasperReportDTO("Nicholas", "Hawaii", "Pipeline", "Hawaii", "nich", "ddd"), Set.of(item),
				"Message");
		OrderReport budgetReport = new OrderReportImpl(new JasperService());
		byte[] bytes = budgetReport.export(budgetDTO, OrderExportType.EXCEL);

	}
}
