package com.portal.java.microsoft.excel;

import java.io.IOException;
import java.io.InputStream;
import java.util.Deque;

public interface XssfReader {

	void read(RowObject rowObject, InputStream xlsxFile) throws IOException;

	Deque<RowObject> read(int fromIndex, int endIndex, InputStream xlsxFile) throws IOException;
}
