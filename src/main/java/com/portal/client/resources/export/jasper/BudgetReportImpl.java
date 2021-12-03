package com.portal.client.resources.export.jasper;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.portal.client.dto.BudgetJasperData;
import com.portal.client.dto.BudgetJasperForm;
import com.portal.client.resources.export.jasper.service.JasperReportType;
import com.portal.client.resources.export.jasper.service.SimpleJasperServiceFactory;
import com.portal.client.security.user.RepresentativeUser.SaleType;

import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@ApplicationScoped
public class BudgetReportImpl implements BudgetReport {

	@Inject
	private SimpleJasperServiceFactory factory;

	@Override
	public byte[] generate(BudgetJasperForm form, JasperReportType type) {
		BudgetJasperData data = form.getData();
		Map<String, Object> params = this.configureLayout(form.getSellertype());
		params.put(JRParameter.REPORT_LOCALE, new Locale("pt", "BR"));
		params.put("itemsCollection", new JRBeanCollectionDataSource(data.getItems()));
		return factory.getService(type).export(getClass().getResourceAsStream("/report/budget.jasper"), params, data);
	}

	private String getLogo(SaleType type) {
		try {
			switch (type) {
			case AGRICOLA:
				return getClass().getResource("/report/images/gaussAgricola.jpeg").toURI().getPath();
			case MOTOS:
				return getClass().getResource("/report/images/gaussMoto.jpeg").toURI().getPath();
			default:
				return getClass().getResource("/report/images/gauss.png").toURI().getPath();
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}

	private String getTitle(SaleType saleType) {
		String title = "Gauss Indústria %s";
		return String.format(title, saleType.getReportLabel());

	}

	public Map<String, Object> configureLayout(SaleType type) {
		Map<String, Object> params = new HashMap<>();

		params.put("logoGaussPath", getLogo(type));
		params.put("title", getTitle(type));
		return params;
	}
}
