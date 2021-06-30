package com.portal.java.microsoft.excel.writer;

import java.util.List;

public interface XssfWriter {

	byte[] write(List<WriteRowObject> rowObjects);
}
