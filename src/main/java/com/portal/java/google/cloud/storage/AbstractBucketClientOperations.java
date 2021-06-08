package com.portal.java.google.cloud.storage;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.Storage.BlobField;
import com.google.cloud.storage.Storage.BlobGetOption;
import com.google.cloud.storage.StorageOptions;

public abstract class AbstractBucketClientOperations {

	private final Storage storage;
	private final String bucket;

	public AbstractBucketClientOperations(String bucket) {
		super();
		storage = StorageOptions.getDefaultInstance().getService();
		this.bucket = bucket;

	}

	protected Blob getObject(String blob) {
		Blob objectOnStrage = storage.get(BlobId.of(bucket, blob), BlobGetOption.fields(BlobField.NAME));
		return objectOnStrage;
	}

	protected Stream<Blob> getObject(List<String> blobsName) {
		List<BlobId> blobsIdToFetch = blobsName.parallelStream().map(s -> BlobId.of(bucket, s)).collect(CopyOnWriteArrayList::new,
				List::add, List::addAll);
		List<Blob> blobs = storage.get(blobsIdToFetch);
		return blobs.parallelStream();
	}
}
