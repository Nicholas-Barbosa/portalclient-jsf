package com.portal.java.resources.export.excel;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.CellType;
import org.junit.jupiter.api.Test;

import com.farawaybr.portal.microsoft.excel.CellAttribute;
import com.farawaybr.portal.microsoft.excel.RowObject;
import com.farawaybr.portal.microsoft.excel.writer.XssfWriter;
import com.farawaybr.portal.microsoft.excel.writer.XssfWriterImpl;

class XssfWriterImplTest {

	@Test
	void test() {
		List<CellAttribute> attributes = new ArrayList<>();
		attributes.add(new CellAttribute(0, "NOME", CellType.STRING));
		attributes.add(new CellAttribute(1, "IDADE", CellType.STRING));
		RowObject row1 = new RowObject(0, attributes);

		List<CellAttribute> attributesForRow2 = new ArrayList<>();
		attributesForRow2.add(new CellAttribute(0, "Nicholas", CellType.STRING));
		attributesForRow2.add(new CellAttribute(1,
				new BigDecimal(49.530000000000001136868377216160297393798828125), CellType.STRING));
		RowObject row2 = new RowObject(1, attributesForRow2);

		XssfWriter writer = new XssfWriterImpl();
		byte[] streams = writer.write("sheet1",List.of(row1, row2));
		try (OutputStream out = new BufferedOutputStream(
				new FileOutputStream("C:\\Users\\nicho\\OneDrive\\Documentos\\testing_excel-poi\\writing.xlsx"))) {
			out.write(streams);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
