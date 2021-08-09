package com.portal.client.service.microsoft.excel.writer;

import java.util.List;

import com.portal.client.service.microsoft.excel.RowObject;

public interface XssfWriter {

	byte[] write(List<RowObject> rowObjects);
}
