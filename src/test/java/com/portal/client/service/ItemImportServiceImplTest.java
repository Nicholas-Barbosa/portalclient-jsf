package com.portal.client.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

import com.portal.client.dto.ItemXlsxFileLayout;
import com.portal.client.microsoft.excel.reader.XssfReaderImpl;

class ItemImportServiceImplTest {

	private final ItemImportService importer = new ItemImportServiceImpl(new XssfReaderImpl(),null);

	@Test
	void test() {
		ItemXlsxFileLayout fileLayout = new ItemXlsxFileLayout();
		fileLayout.setOffSetCellForProductCode(0);
		fileLayout.setOffSetCellForProductQuantity(1);
		fileLayout.setInitPosition(1);
		try (InputStream in = new FileInputStream("C:\\Users\\nicho\\OneDrive\\Documentos\\testImportBudget.xlsx")) {
			fileLayout.setXlsxStreams(in.readAllBytes());
			System.out.println(importer.read(fileLayout));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
