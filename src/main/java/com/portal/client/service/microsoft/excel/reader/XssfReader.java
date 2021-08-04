package com.portal.client.service.microsoft.excel.reader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Deque;

public interface XssfReader {

	void read(RowObject rowObject, InputStream xlsxInputStream) throws IOException;

	void read(RowObject rowObject, byte[] xlsxStreams) throws IOException;

	Deque<RowObject> read(InputStream xlsxInputStream) throws IOException;

	Deque<RowObject> read(byte[] xlsxStreams) throws IOException;
}
