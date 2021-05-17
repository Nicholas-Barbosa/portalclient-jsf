package com.portal.jasper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import com.portal.dto.BudgetEstimateDTO;
import com.portal.dto.BudgetEstimateDTO.EstimatedItem;
import com.portal.dto.CustomerDTO;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

class JasperServiceTest {

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@Test
	void test() {
		try (InputStream in = new BufferedInputStream(getClass().getResourceAsStream("/report/budgetEstimate.jasper"));
				OutputStream out = new BufferedOutputStream(
						new FileOutputStream("C:\\Users\\nicho\\OneDrive\\Documentos\\filledReports\\fill"))) {
			BudgetEstimateDTO budgetEstimate = new BudgetEstimateDTO(new BigDecimal(1200), new BigDecimal(8000),
					List.of(new EstimatedItem(new BigDecimal(10), new BigDecimal(10), "GX901", "AB001",
							new BigDecimal(10), 20, new BigDecimal(10), new BigDecimal(10))),
					null, new CustomerDTO("RUA PETRONIO TENORIO DE MOURA,11 BR 232", "", "01", "Paraná",
							"10685535000105", "", "ANTONIO ROZENDO DA SILVA PECAS", "", "Curitiba", ""));
			JRBeanCollectionDataSource itemsCollection = new JRBeanCollectionDataSource(
					budgetEstimate.getEstimatedItemValues());
			Map<String, Object> params = new HashMap<>();
			params.put("itemsCollection", itemsCollection);
			JasperFillManager.fillReportToStream(in, out, params,
					new JRBeanCollectionDataSource(List.of(budgetEstimate)));

			JasperExportManager.exportReportToPdfFile("C:\\Users\\nicho\\OneDrive\\Documentos\\filledReports\\fill",
					"C:\\Users\\nicho\\OneDrive\\Documentos\\filledReports\\exported.pdf");
			// JRXlsExporter xlExporter = new JRXlsExporter();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
