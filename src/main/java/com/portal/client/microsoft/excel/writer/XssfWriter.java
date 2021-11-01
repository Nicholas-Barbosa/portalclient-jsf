package com.portal.client.microsoft.excel.writer;

import java.util.List;

import com.portal.client.microsoft.excel.RowObject;

public interface XssfWriter {

	byte[] write(String sheetName,List<RowObject> rowObjects);
}
