package com.portal.java.export;

import java.util.Collection;

import com.portal.java.dto.FinancialBondsPageDTO.FinacialBondsDTO;

public interface FinancialBondsExporter {

	byte[] toExcel(int numberOfPages, int pageSize);

	byte[] toExcel(Collection<? extends FinacialBondsDTO> financialBonds);
}
