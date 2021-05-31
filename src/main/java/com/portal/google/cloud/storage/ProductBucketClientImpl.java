package com.portal.google.cloud.storage;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.StorageOptions;
import com.portal.cdi.qualifier.ProductBucket;
import com.portal.google.cloud.storage.manager.BucketStateManager;

@ApplicationScoped
@ProductBucket
public class ProductBucketClientImpl extends AbstractBucketClientOperations implements BucketClient {

	private static final String BUCKET_NAME = "streams-portal";

	private static final Bucket BUCKET = StorageOptions.getDefaultInstance().getService().get(BUCKET_NAME);

	@Inject
	private BucketStateManager imageManagerLifeCycle;

	public ProductBucketClientImpl() {
		super(BUCKET);
	}

	@Override
	public Blob getObject(String objectName) {
		 imageManagerLifeCycle.initLoadingImage(objectName);
		Blob image = super.getObject(formatBlobName(objectName));
		imageManagerLifeCycle.loadedImage(objectName);
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
}
