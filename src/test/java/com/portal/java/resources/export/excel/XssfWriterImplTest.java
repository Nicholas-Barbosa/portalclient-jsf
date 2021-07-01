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

import com.portal.java.microsoft.excel.writer.WriteCellAttribute;
import com.portal.java.microsoft.excel.writer.WriteRowObject;
import com.portal.java.microsoft.excel.writer.XssfWriter;
import com.portal.java.microsoft.excel.writer.XssfWriterImpl;

class XssfWriterImplTest {

	@Test
	void test() {
		List<WriteCellAttribute> attributes = new ArrayList<>();
		attributes.add(new WriteCellAttribute(0,"NOME", CellType.STRING));
		attributes.add(new WriteCellAttribute(1,"IDADE", CellType.STRING));
		WriteRowObject row1 = new WriteRowObject(0,attributes);

		List<WriteCellAttribute> attributesForRow2 = new ArrayList<>();
		attributesForRow2.add(new WriteCellAttribute(0,"Nicholas", CellType.STRING));
		attributesForRow2.add(new WriteCellAttribute(1,new BigDecimal(49.530000000000001136868377216160297393798828125), CellType.STRING));
		WriteRowObject row2 = new WriteRowObject(1,attributesForRow2);

		XssfWriter writer = new XssfWriterImpl();
		byte[] streams = writer.write(List.of(row1, row2));
		try (OutputStream out = new BufferedOutputStream(new FileOutputStream("C:\\Users\\nicho\\OneDrive\\Documentos\\testing_excel-poi\\writing.xlsx"))) {
			out.write(streams);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
