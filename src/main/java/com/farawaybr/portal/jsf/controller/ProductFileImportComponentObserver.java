package com.farawaybr.portal.jsf.controller;

import com.farawaybr.portal.dto.BatchProductSearchDataWrapper;

public interface ProductFileImportComponentObserver {

	void onConfirm(BatchProductSearchDataWrapper wrapper);
}
