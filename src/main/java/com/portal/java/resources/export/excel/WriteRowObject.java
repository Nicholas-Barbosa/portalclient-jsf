package com.portal.java.resources.export.excel;

import java.util.ArrayList;
import java.util.List;

public class WriteRowObject {

	private List<WriteCellAttribute> attributes;

	public WriteRowObject(List<WriteCellAttribute> attributes) {
		super();
		this.attributes = new ArrayList<>(attributes);
	}

	public List<WriteCellAttribute> getAttributes() {
		return new ArrayList<>(attributes);
	}

}
