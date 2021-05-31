package com.portal.google.cloud.storage;

import java.util.List;
import java.util.stream.Stream;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;

public abstract class AbstractBucketClientOperations {

	private final Bucket bucket;

	public AbstractBucketClientOperations(Bucket bucket) {
		super();
		this.bucket = bucket;
	}

	protected Blob getObject(String blob)  {
		Blob objectOnStrage = bucket.get(blob);
		return objectOnStrage;
	}

	protected Stream<Blob> getObject(List<String> blobsName) {
		List<Blob> blobs = bucket.get(blobsName);
		return blobs.parallelStream();
	}
}
