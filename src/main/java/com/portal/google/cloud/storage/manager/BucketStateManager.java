package com.portal.google.cloud.storage.manager;

import com.portal.google.cloud.storage.manager.ProductBucketStateManager.State;

public interface BucketStateManager {

	void initLoadingImage(String image);

	void loadedImage(String image);

	State getState(String image);
}
