package com.portal.google.cloud.storage;

import java.io.Serializable;

public interface ProductBucketClient extends BucketClient, Serializable {

	byte[]getProduct(String code);
	
	void serializeProducts();
}
