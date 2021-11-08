package com.portal.jasper.service;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.portal.client.dto.BudgetJasperForm;
import com.portal.client.security.user.RepresentativeUser.SaleType;
import com.portal.client.service.export.OrderExportType;
import com.portal.client.service.export.jasper.BudgetJasperData;
import com.portal.client.service.export.jasper.BudgetReport;
import com.portal.client.service.export.jasper.BudgetReportImpl;
import com.portal.client.service.export.jasper.JasperService;
import com.portal.client.service.export.jasper.BudgetJasperData.CustomerJasperReportDTO;
import com.portal.client.service.export.jasper.BudgetJasperData.OrderItemJasper;

class BudgetReportImplTest {

	private final OrderItemJasper item = new OrderItemJasper("AX001", "LAMPADA", 10, BigDecimal.TEN, BigDecimal.TEN,
			BigDecimal.TEN, BigDecimal.TEN, BigDecimal.TEN, BigDecimal.TEN, BigDecimal.TEN);

	@Test
	void test() {
		BudgetJasperData budgetDTO = new BudgetJasperData(new BigDecimal(12.99), new BigDecimal(20.98),
				new BigDecimal(49.530000000000001136868377216160297393798828125),
				new CustomerJasperReportDTO("Nicholas", "Hawaii", "Pipeline", "Hawaii", "82828373", "ddd"),
				Set.of(item), "Mensagem","Nicholas");
		BudgetReport budgetReport = new BudgetReportImpl(new JasperService());
		byte[] bytes = budgetReport.process(new BudgetJasperForm(SaleType.MOTOS, budgetDTO),
				OrderExportType.PDF);
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
		BudgetJasperData budgetDTO = new BudgetJasperData(new BigDecimal(12.99), new BigDecimal(20.98),
				new BigDecimal(49.530000000000001136868377216160297393798828125),
				new CustomerJasperReportDTO("Nicholas", "Hawaii", "Pipeline", "Hawaii", "nich", "ddd"), Set.of(item),
				"Message","Nicholas");
		BudgetReport budgetReport = new BudgetReportImpl(new JasperService());
		byte[] bytes = budgetReport.process(new BudgetJasperForm(SaleType.MOTOS, budgetDTO),
				OrderExportType.EXCEL);

	}
}
