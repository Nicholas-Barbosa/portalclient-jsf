package com.portal.client.export;

import java.util.Collection;

import com.portal.client.dto.FinancialBondsPage.FinacialBondsDTO;

public interface FinancialBondsExporter {

	byte[] toExcel(int numberOfPages, int pageSize);

	byte[] toExcel(Collection<? extends FinacialBondsDTO> financialBonds);
}
