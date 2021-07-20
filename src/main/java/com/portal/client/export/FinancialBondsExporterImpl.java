package com.portal.client.export;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.portal.client.microsoft.excel.writer.WriteRowObject;
import com.portal.client.microsoft.excel.writer.XssfWriter;
import com.portal.client.microsoft.excel.writer.WriteCellAttribute.WriteCellAttributeBuilder;
import com.portal.client.vo.FinancialBondsPage.FinacialBondsDTO;

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
		List<WriteRowObject> rowObjects = new ArrayList<>();
		rowObjects.add(new WriteRowObject(0,
				WriteCellAttributeBuilder.of(0, "Número documento", "Cliente", "Venda", "Vencimento", "Situação")));

		final AtomicInteger count = new AtomicInteger(1);
		rowObjects.addAll(financialBonds.parallelStream().map(f -> {
			return new WriteRowObject(count.getAndIncrement(), WriteCellAttributeBuilder.of(0, f.getDocNumber(),
					f.getCustomerName(), f.getSale(), f.getDueDate(), f.getSituation()));
		}).collect(CopyOnWriteArrayList::new, List::add, List::addAll));
		
		return xssfWriter.write(rowObjects);
	}

}
