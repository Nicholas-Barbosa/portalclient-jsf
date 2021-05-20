package com.portal.jasper.service;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import com.portal.dto.BudgetEstimateDTO.EstimatedItem;
import com.portal.dto.BudgetJasperReportDTO;
import com.portal.dto.BudgetJasperReportDTO.CustomerJasperReportDTO;
import com.portal.jasper.ReportService;

class BudgetReportImplTest {

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@Test
	void test() {
		BudgetJasperReportDTO budgetDTO = new BudgetJasperReportDTO(new BigDecimal(12.99), new BigDecimal(20.98),
				new BigDecimal(49.530000000000001136868377216160297393798828125),
				new CustomerJasperReportDTO("Nicholas", "Hawaii", "Pipeline", "Hawaii", "82828373"),
				List.of(new EstimatedItem(new BigDecimal(10), new BigDecimal(10), "GX901", "AB001", new BigDecimal(10),
						20, new BigDecimal(10), new BigDecimal(10))));
		BudgetReport budgetReport = new BudgetReportImpl(new ReportService());
		byte[] bytes = budgetReport.toPdf(budgetDTO);
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
		BudgetJasperReportDTO budgetDTO = new BudgetJasperReportDTO(new BigDecimal(12.99), new BigDecimal(20.98),
				new BigDecimal(49.530000000000001136868377216160297393798828125),
				new CustomerJasperReportDTO("Nicholas", "Hawaii", "Pipeline", "Hawaii", "nich"),
				List.of(new EstimatedItem(new BigDecimal(10), new BigDecimal(10), "GX901", "AB001", new BigDecimal(10),
						20, new BigDecimal(10), new BigDecimal(10))));
		BudgetReport budgetReport = new BudgetReportImpl(new ReportService());
		byte[] bytes = budgetReport.toExcel(budgetDTO);

	}
}
