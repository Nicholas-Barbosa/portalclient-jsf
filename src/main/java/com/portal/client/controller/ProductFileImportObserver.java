package com.portal.client.controller;

import com.portal.client.dto.BatchProductSearchDataWrapper;

public interface ProductFileImportObserver {

	void onConfirm(BatchProductSearchDataWrapper wrapper);
}
