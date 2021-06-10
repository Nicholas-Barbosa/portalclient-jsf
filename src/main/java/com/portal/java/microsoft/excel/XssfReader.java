package com.portal.java.microsoft.excel;

import java.io.InputStream;

public interface XssfReader {

	void read(RowObject rowObject, InputStream xlsxFile);
}
