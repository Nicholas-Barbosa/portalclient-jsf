package com.farawaybr.portal.resources.poi.microsoft.excel.writer;

import java.util.List;

import com.farawaybr.portal.resources.poi.microsoft.excel.RowObject;

public interface XssfWriter {

	byte[] write(String sheetName,List<RowObject> rowObjects);
}
