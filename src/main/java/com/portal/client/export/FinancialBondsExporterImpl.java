package com.portal.client.export;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.portal.client.dto.FinancialBondsPage.FinacialBondsDTO;
import com.portal.client.service.microsoft.excel.RowObject;
import com.portal.client.service.microsoft.excel.writer.WriteCellAttribute.WriteCellAttributeBuilder;
import com.portal.client.service.microsoft.excel.writer.XssfWriter;

@ApplicationScoped
public class FinancialBondsExporterImpl implements FinancialBondsExporter {

	private XssfWriter xssfWriter;

	@Inject
	public FinancialBondsExporterImpl(XssfWriter xssfWriter) {
		super();
		this.xssfWriter = xssfWriter;
	}

	@Override
	public byte[] toExcel(int numberOfPages, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] toExcel(Collection<? extends FinacialBondsDTO> financialBonds) {
		List<RowObject> rowObjects = new ArrayList<>();
		rowObjects.add(new RowObject(0,
				WriteCellAttributeBuilder.of(0, "Número documento", "Cliente", "Venda", "Vencimento", "Situação")));

		final AtomicInteger count = new AtomicInteger(1);
		rowObjects.addAll(financialBonds.parallelStream().map(f -> {
			return new RowObject(count.getAndIncrement(), WriteCellAttributeBuilder.of(0, f.getDocNumber(),
					f.getCustomerName(), f.getSale(), f.getDueDate(), f.getSituation()));
		}).collect(CopyOnWriteArrayList::new, List::add, List::addAll));

		return xssfWriter.write(rowObjects);
	}
	
}
