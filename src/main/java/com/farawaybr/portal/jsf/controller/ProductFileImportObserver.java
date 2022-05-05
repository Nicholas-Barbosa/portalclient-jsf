package com.farawaybr.portal.jsf.controller;

import com.farawaybr.portal.dto.BatchProductSearchDataWrapper;

public interface ProductFileImportObserver {

	void onConfirm(BatchProductSearchDataWrapper wrapper);
}
