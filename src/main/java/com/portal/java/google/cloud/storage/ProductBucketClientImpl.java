package com.portal.java.google.cloud.storage;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Stream;

import javax.enterprise.context.ApplicationScoped;

import com.google.cloud.storage.Blob;
import com.portal.java.cdi.qualifier.ProductBucket;

@ApplicationScoped
@ProductBucket
public class ProductBucketClientImpl extends AbstractBucketClientOperations implements BucketClient {

	private static final String BUCKET_NAME = "streams-portal";

	public ProductBucketClientImpl() {
		super(BUCKET_NAME);
	}

	@Override
	public Blob getObject(String objectName) {
		Blob image = super.getObject(formatBlobName(objectName.toUpperCase()));
		return image;
	}

	@Override
	public Stream<Blob> getObjects(List<String> blobs) {
		// TODO Auto-generated method stub
		return super.getObject(blobs.parallelStream().map(this::formatBlobName).collect(CopyOnWriteArrayList::new,
				List::add, List::addAll));
	}

	@Override
	public Future<Blob> getAsyncObject(String blob) {
		ExecutorService executor = null;
		try {
			executor = Executors.newSingleThreadExecutor();
			return executor.submit(() -> {
				return super.getObject(formatBlobName(blob.toUpperCase()));
			});
		} finally {
			executor.shutdown();
		}
	}

	private String formatBlobName(String blobName) {
		return String.format("%s/%s.JPG", "imagens_tratadas", blobName);
	}

}
