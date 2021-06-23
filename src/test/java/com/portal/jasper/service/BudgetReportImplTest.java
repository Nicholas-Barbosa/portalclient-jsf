package com.portal.jasper.service;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import com.portal.java.dto.BudgetJasperReportDTO;
import com.portal.java.dto.BudgetJasperReportDTO.CustomerJasperReportDTO;
import com.portal.java.dto.EstimatedItemDTO;
import com.portal.java.dto.Item;
import com.portal.java.jasper.ReportService;
import com.portal.java.jasper.service.BudgetReport;
import com.portal.java.jasper.service.BudgetReportImpl;

class BudgetReportImplTest {

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	private final Item item = new Item(new BigDecimal(135.98), new BigDecimal(900), "AX001",
			"AX001", new BigDecimal(135.98), 20, new BigDecimal(900), new BigDecimal(18.89), new BigDecimal(12.89),
			234);
	{
		item.setDescription("BOBINA DE IGNICAO GC4029");
	}

	@Test
	void test() {
		BudgetJasperReportDTO budgetDTO = new BudgetJasperReportDTO(new BigDecimal(12.99), new BigDecimal(20.98),
				new BigDecimal(49.530000000000001136868377216160297393798828125),
				new CustomerJasperReportDTO("Nicholas", "Hawaii", "Pipeline", "Hawaii", "82828373"), Set.of(item));
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
				new CustomerJasperReportDTO("Nicholas", "Hawaii", "Pipeline", "Hawaii", "nich"), Set.of(item));
		BudgetReport budgetReport = new BudgetReportImpl(new ReportService());
		byte[] bytes = budgetReport.toExcel(budgetDTO);

	}
}
