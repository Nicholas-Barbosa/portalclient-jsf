package com.portal.client.service;

import java.util.List;

import com.portal.client.dto.BaseBudget;
import com.portal.client.dto.ItemXlsxFileLayout;
import com.portal.client.dto.ItemXlsxProjection;
import com.portal.client.exception.CustomerNotFoundException;
import com.portal.client.exception.ItemsNotFoundException;

public interface ItemImportService {

	List<ItemXlsxProjection> read(ItemXlsxFileLayout fileLayout);

	BaseBudget findPrice(List<ItemXlsxProjection> items, String customerCode, String customerStore)
			throws CustomerNotFoundException, ItemsNotFoundException;
}
