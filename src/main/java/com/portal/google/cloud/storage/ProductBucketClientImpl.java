package com.portal.google.cloud.storage;

import java.util.List;
import java.util.stream.Stream;

import javax.ejb.Singleton;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.StorageOptions;
import com.portal.cdi.qualifier.ProductBucket;

@Singleton
@ProductBucket
public class ProductBucketClientImpl extends AbstractBucketClientOperations implements BucketClient {

	private static final String BUCKET_NAME = "streams-portal";

	private static final Bucket BUCKET = StorageOptions.getDefaultInstance().getService().get(BUCKET_NAME);

	public ProductBucketClientImpl() {
		super(BUCKET);
	}

	@Override
	public Blob getObject(String objectName) {
		return super.getObject(objectName);
	}

	@Override
	public Stream<Blob> getObjects(List<String> blobs) {
		// TODO Auto-generated method stub
		return super.getObject(blobs);
	}

}
