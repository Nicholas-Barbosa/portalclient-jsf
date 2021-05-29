package com.portal.google.cloud.storage;

import javax.ejb.Singleton;

import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.StorageOptions;

@Singleton
public class ProductBucketClientImpl implements ProductBucketClient {

	/**
	 * 
	 */
	private static final long serialVersionUID = -323442013925782190L;

	private static final String BUCKET_NAME = "streams-portal";

	private static final Bucket BUCKET = StorageOptions.getDefaultInstance().getService().get(BUCKET_NAME);

	@Override
	public byte[] getObject(String objectName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] deflate(byte[] inflated) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] inflate(byte[] deflated) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] getProduct(String code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void serializeProducts() {
		// TODO Auto-generated method stub

	}

}
