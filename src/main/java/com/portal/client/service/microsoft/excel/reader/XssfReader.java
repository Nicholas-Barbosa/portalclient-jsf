package com.portal.client.service.microsoft.excel.reader;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface XssfReader {

	void read(RowObject rowObject, InputStream xlsxInputStream) throws IOException;

	void read(RowObject rowObject, byte[] xlsxStreams) throws IOException;

	void read(List<RowObject> rowObject, InputStream xlsxInputStream) throws IOException;
	
	void read(List<RowObject> rowObject, byte[] xlsxStreams) throws IOException;
}
