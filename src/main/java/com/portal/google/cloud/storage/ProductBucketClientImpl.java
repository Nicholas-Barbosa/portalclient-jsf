package com.portal.google.cloud.storage;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Stream;

import javax.enterprise.context.ApplicationScoped;

import com.google.cloud.storage.Blob;
import com.portal.cdi.qualifier.ProductBucket;

@ApplicationScoped
@ProductBucket
public class ProductBucketClientImpl extends AbstractBucketClientOperations implements BucketClient {

	private static final String BUCKET_NAME = "streams-portal";

	public ProductBucketClientImpl() {
		super(BUCKET_NAME);
	}

	@Override
	public Blob getObject(String objectName) {
		Blob image = super.getObject(formatBlobName(objectName));
		return image;
	}

	@Override
	public Stream<Blob> getObjects(List<String> blobs) {
		// TODO Auto-generated method stub
		return super.getObject(blobs.parallelStream().map(this::formatBlobName).collect(CopyOnWriteArrayList::new,
				List::add, List::addAll));
	}

	private String formatBlobName(String blobName) {
		return String.format("%s/%s.JPG", "imagens_tratadas", blobName);
	}

	@Override
	public Future<Blob> getAsyncObject(String blob) {
		ExecutorService executor = null;
		try {
			executor = Executors.newSingleThreadExecutor();
			return executor.submit(() -> super.getObject(blob));
		} finally {
			executor.shutdown();
		}
	}
}
