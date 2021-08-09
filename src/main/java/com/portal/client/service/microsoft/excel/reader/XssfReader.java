package com.portal.client.service.microsoft.excel.reader;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.portal.client.service.microsoft.excel.RowObject;

public interface XssfReader {

	List<RowObject> read(InputStream xlsxInputStream, short initialOffset, short endOffset) throws IOException;

	List<RowObject> read(byte[] xlsxStreams, short initialOffset, short endOffset) throws IOException;
}
