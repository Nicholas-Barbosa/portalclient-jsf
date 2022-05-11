package com.farawaybr.portal.resources.export;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.farawaybr.portal.microsoft.excel.RowObject;
import com.farawaybr.portal.microsoft.excel.CellAttribute.CellAttributeBuilder;
import com.farawaybr.portal.microsoft.excel.writer.XssfWriter;
import com.farawaybr.portal.vo.WrapperProduct404Error.Product404Error;

@ApplicationScoped
public class XlsxProductImportComponentNotFoundCommandExporter
		implements ProductsImportComponentNotFoundCommandExporter {

	@Inject
	private XssfWriter writer;

	@Override
	public byte[] execute(Product404Error[] products) {
		final AtomicInteger rowOffset = new AtomicInteger(1);
		List<RowObject> rows = new ArrayList<>();
		rows.add(new RowObject(0, CellAttributeBuilder.of(0, "Produto")));
		rows.addAll(Arrays.stream(products).map(
				p -> new RowObject(rowOffset.getAndIncrement(), CellAttributeBuilder.of(0, p.getProductIdentity())))
				.collect(Collectors.toList()));
		return writer.write("Produtos inconsistentes", rows);
	}

}
