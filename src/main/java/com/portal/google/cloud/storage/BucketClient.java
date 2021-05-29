package com.portal.google.cloud.storage;

public interface BucketClient {

	byte[] getObject(String objectName);
	
	byte[] deflate(byte[]inflated);
	
	byte[]inflate(byte[]deflated);
}
