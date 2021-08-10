package com.portal.client.service;

import java.util.List;

import com.portal.client.dto.ItemXlsxFileLayout;
import com.portal.client.dto.ItemXlsxProjection;

public interface ItemImportService {

	List<ItemXlsxProjection> read(ItemXlsxFileLayout fileLayout);
}
