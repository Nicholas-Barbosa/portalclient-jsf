package com.farawaybr.portal.microsoft.excel.writer;

import java.util.List;

import com.farawaybr.portal.microsoft.excel.RowObject;

public interface XssfWriter {

	byte[] write(String sheetName,List<RowObject> rowObjects);
}
