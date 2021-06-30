package com.portal.java.resources.export.excel;

import java.util.List;

public interface XssfWriter {

	byte[] write(List<WriteRowObject> rowObjects);
}
